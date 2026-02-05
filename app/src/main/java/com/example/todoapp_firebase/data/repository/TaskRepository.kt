package com.example.todoapp_firebase.data.repository

import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.data.model.Task
import kotlinx.coroutines.flow.Flow

/** Gemini início
 *
 * Prompt: Crie uma interface TaskRepository para as operações de CRUD (Get, Add, Update, Delete). A função de pegar tarefas (getTasks) deve retornar um Flow de Response List Task para atualizações em tempo real.
 *
 */
interface TaskRepository {

    // Usamos Flow aqui para receber atualizações em tempo real do Firestore
    fun getTasks(): Flow<Response<List<Task>>>

    suspend fun getTask(id: String): Response<Task>

    suspend fun addTask(title: String, description: String): Response<Boolean>

    suspend fun updateTask(task: Task): Response<Boolean>

    suspend fun deleteTask(taskId: String): Response<Boolean>
}
/** Gemini final */