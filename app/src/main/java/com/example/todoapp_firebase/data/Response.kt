package com.example.todoapp_firebase.data

/** Gemini início
 *
 * Prompt: Crie uma sealed class genérica chamada Response para gerenciar estados de Loading, Success e Error.
 *
 */
sealed class Response<out T> {
    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Error(
        val message: String
    ) : Response<Nothing>()
}
/** Gemini final */