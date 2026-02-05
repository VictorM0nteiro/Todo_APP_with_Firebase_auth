package com.example.todoapp_firebase.data.repository

import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.data.model.Task
import com.example.todoapp_firebase.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/** Gemini início
 *
 * Prompt: Implemente a TaskRepositoryImpl injetando FirebaseFirestore e FirebaseAuth.
 * No método getTasks, use callbackFlow para escutar mudanças em tempo real na coleção "tasks", filtrando pelo userId do usuário logado.
 * Implemente addTask, updateTask e deleteTask usando as funções do Firestore (add, set, delete) com await().
 *
 */
class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TaskRepository {

    private val tasksCollection = firestore.collection(Constants.TASKS_COLLECTION)

    override fun getTasks(): Flow<Response<List<Task>>> = callbackFlow {
        // Pega o ID do usuário atual para filtrar apenas as tarefas dele
        val userId = auth.currentUser?.uid ?: ""

        if (userId.isEmpty()) {
            trySend(Response.Error("Usuário não logado"))
            close()
            return@callbackFlow
        }

        // Escuta em tempo real (addSnapshotListener)
        val subscription = tasksCollection
            .whereEqualTo("userId", userId)
            // Ordena pela data de criação se tivesse, ou pelo título
            //.orderBy("title", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Response.Error(error.localizedMessage ?: "Erro ao carregar tarefas"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    // Converte os documentos do Firestore para nossa classe Task
                    val tasks = snapshot.toObjects(Task::class.java)
                    trySend(Response.Success(tasks))
                }
            }

        // Quando o fluxo fechar, remove o listener para não gastar memória
        awaitClose { subscription.remove() }
    }

    override suspend fun getTask(id: String): Response<Task> {
        return try {
            val document = tasksCollection.document(id).get().await()
            val task = document.toObject(Task::class.java)
            if (task != null) {
                Response.Success(task)
            } else {
                Response.Error("Tarefa não encontrada")
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro ao buscar tarefa")
        }
    }

    override suspend fun addTask(title: String, description: String): Response<Boolean> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("Usuário não logado")
            val id = tasksCollection.document().id // Gera um ID único

            val task = Task(
                id = id,
                title = title,
                description = description,
                isCompleted = false,
                userId = userId
            )

            tasksCollection.document(id).set(task).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro ao salvar tarefa")
        }
    }

    override suspend fun updateTask(task: Task): Response<Boolean> {
        return try {
            // Atualiza o documento existente com o mesmo ID
            tasksCollection.document(task.id).set(task).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro ao atualizar tarefa")
        }
    }

    override suspend fun deleteTask(taskId: String): Response<Boolean> {
        return try {
            tasksCollection.document(taskId).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro ao deletar tarefa")
        }
    }
}
/** Gemini final */