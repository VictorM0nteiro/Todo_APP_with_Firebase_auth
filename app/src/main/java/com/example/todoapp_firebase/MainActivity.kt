package com.example.todoapp_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.todoapp_firebase.ui.navigation.AppNavHost
import com.example.todoapp_firebase.ui.theme.TodoAPP_firebaseTheme
import dagger.hilt.android.AndroidEntryPoint

/** Gemini início
 *
 * Prompt: Atualize a MainActivity para configurar o NavController usando rememberNavController.
 * Dentro do bloco setContent e do tema, chame o AppNavHost.
 * Mantenha a anotação @AndroidEntryPoint necessária para o Hilt.
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAPP_firebaseTheme {
                // A container surface using the 'background' color from the theme
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
/** Gemini final */