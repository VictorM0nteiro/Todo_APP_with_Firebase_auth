package com.example.todoapp_firebase.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Gemini início
 *
 * Prompt: Crie um AuthViewModel anotado com @HiltViewModel que injeta o AuthRepository.
 * Crie um StateFlow _authState para gerenciar o estado do login (Loading, Success, Error).
 * Implemente funções de login e signUp que chamam o repositório dentro do viewModelScope e atualizam o estado.
 * Crie uma função checkAuthStatus para verificar se o usuário já está logado ao abrir o app.
 *
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<Response<Boolean>?>(null)
    val authState: StateFlow<Response<Boolean>?> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (repo.currentUser != null) {
            _authState.value = Response.Success(true)
        }
    }

    fun login(email: String, pass: String) = viewModelScope.launch {
        _authState.value = Response.Loading
        val result = repo.login(email, pass)
        _authState.value = result
    }

    fun signUp(email: String, pass: String) = viewModelScope.launch {
        _authState.value = Response.Loading
        val result = repo.signUp(email, pass)
        _authState.value = result
    }

    fun signOut() {
        repo.signOut()
        _authState.value = null
    }
}
/** Gemini final */