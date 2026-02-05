package com.example.todoapp_firebase.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.data.model.Task
import com.example.todoapp_firebase.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Gemini início
 *
 * Prompt: Crie um TaskViewModel anotado com @HiltViewModel injetando TaskRepository.
 * Use um StateFlow _tasks para armazenar a lista de tarefas (Response<List<Task>>).
 * No bloco init, chame a função getTasks() para iniciar a escuta em tempo real.
 * Implemente funções para addTask, updateTask, deleteTask e onTaskCheckedChanged (para alternar o status isCompleted).
 *
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repo: TaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<Response<List<Task>>>(Response.Loading)
    val tasks: StateFlow<Response<List<Task>>> = _tasks.asStateFlow()

    private val _addResult = MutableStateFlow<Response<Boolean>?>(null)
    val addResult: StateFlow<Response<Boolean>?> = _addResult

    init {
        getTasks()
    }

    private fun getTasks() = viewModelScope.launch {
        repo.getTasks().collect { response ->
            _tasks.value = response
        }
    }

    fun addTask(title: String, description: String) = viewModelScope.launch {
        _addResult.value = Response.Loading
        _addResult.value = repo.addTask(title, description)
    }

    fun resetAddResult() {
        _addResult.value = null
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repo.updateTask(task)
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        repo.updateTask(task.copy(isCompleted = isChecked))
    }

    fun deleteTask(taskId: String) = viewModelScope.launch {
        repo.deleteTask(taskId)
    }
}
/** Gemini final */