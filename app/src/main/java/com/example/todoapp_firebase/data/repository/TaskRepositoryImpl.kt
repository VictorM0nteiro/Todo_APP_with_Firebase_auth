package com.example.todoapp_firebase.data.repository

import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.data.model.Task
import com.example.todoapp_firebase.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * TaskRepositoryImpl CORRIGIDO
 *
 * Correção principal: updateTask agora usa .update() ao invés de .set()
 * Isso resolve o problema das checkboxes que não funcionavam
 */
class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : TaskRepository {

    private val tasksCollection = firestore.collection(Constants.TASKS_COLLECTION)

    override fun getTasks(): Flow<Response<List<Task>>> = callbackFlow {
        val userId = auth.currentUser?.uid ?: ""

        if (userId.isEmpty()) {
            trySend(Response.Error("Usuário não logado"))
            close()
            return@callbackFlow
        }

        val subscription = tasksCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Response.Error(error.localizedMessage ?: "Erro ao carregar tarefas"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val tasks = snapshot.toObjects(Task::class.java)
                    trySend(Response.Success(tasks))
                }
            }

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
            val id = tasksCollection.document().id

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
            // CORREÇÃO PRINCIPAL: Usar .update() com mapOf ao invés de .set()
            // Isso evita conflitos com @DocumentId e atualiza apenas os campos necessários
            val updates = hashMapOf<String, Any>(
                "title" to task.title,
                "description" to task.description,
                "isCompleted" to task.isCompleted,
                "userId" to task.userId
            )

            tasksCollection.document(task.id).update(updates).await()
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