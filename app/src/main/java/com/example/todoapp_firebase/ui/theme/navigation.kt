package com.example.todoapp_firebase.ui.navigation

/** Gemini início
 *
 * Prompt: Crie uma sealed class Routes para definir as strings das rotas de navegação do app. Defina objetos para Login, SignUp e Home.
 *
 */
sealed class Routes(val route: String) {
    object Login : Routes("login")
    object SignUp : Routes("signup")
    object Home : Routes("home")
}
/** Gemini final */