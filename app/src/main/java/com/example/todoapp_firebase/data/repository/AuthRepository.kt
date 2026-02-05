package com.example.todoapp_firebase.data.repository

import com.example.todoapp_firebase.data.Response
import com.google.firebase.auth.FirebaseUser

/** Gemini início
 *
 * Prompt: Crie uma interface AuthRepository com funções suspensas para login, cadastro, logout e uma propriedade para obter o usuário atual (currentUser). As funções devem retornar a classe Response.
 *
 */
interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Response<Boolean>

    suspend fun signUp(email: String, password: String): Response<Boolean>

    fun signOut()
}
/** Gemini final */