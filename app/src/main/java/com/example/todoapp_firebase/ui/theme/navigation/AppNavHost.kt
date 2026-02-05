package com.example.todoapp_firebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp_firebase.ui.auth.LoginScreen
import com.example.todoapp_firebase.ui.auth.SignUpScreen
import com.example.todoapp_firebase.ui.task.ListScreen

/** Gemini início
 *
 * Prompt: Crie um Composable AppNavHost que recebe um NavController e uma startDestination (padrão Routes.Login.route).
 * Configure o NavHost com as rotas definidas na sealed class Routes.
 * Defina os composables para LoginScreen, SignUpScreen e ListScreen, passando o navController para eles.
 *
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Routes.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(Routes.Home.route) {
            ListScreen(navController = navController)
        }
    }
}
/** Gemini final */