package com.example.todoapp_firebase.data.repository

import com.example.todoapp_firebase.data.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/** Gemini início
 *
 * Prompt: Implemente a classe AuthRepositoryImpl que herda de AuthRepository. Use @Inject para receber o FirebaseAuth. Implemente os métodos login e signUp usando await() para converter as tarefas do Firebase em coroutines. Trate exceções retornando Response.Error.
 *
 */
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser = auth.currentUser

    override suspend fun login(email: String, password: String): Response<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro desconhecido no login")
        }
    }

    override suspend fun signUp(email: String, password: String): Response<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "Erro ao criar conta")
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}
/** Gemini final */