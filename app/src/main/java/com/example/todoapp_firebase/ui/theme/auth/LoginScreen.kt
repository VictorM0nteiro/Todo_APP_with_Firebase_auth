package com.example.todoapp_firebase.ui.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp_firebase.data.Response
import com.example.todoapp_firebase.ui.components.CustomButton
import com.example.todoapp_firebase.ui.components.CustomTextField
import com.example.todoapp_firebase.ui.navigation.Routes

/** Gemini início
 *
 * Prompt: Crie uma tela LoginScreen que recebe um NavController e usa hiltViewModel para obter o AuthViewModel.
 * Use um Column para alinhar os elementos ao centro.
 * Adicione campos de email e senha usando CustomTextField.
 * Adicione um CustomButton que chama viewModel.login().
 * Adicione um Text clicável para navegar para a tela de SignUp.
 * Use LaunchedEffect para observar o authState. Se for Success, navegue para Routes.Home. Se for Error, mostre um Toast.
 *
 */
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    // Observa o estado da autenticação
    val authState = viewModel.authState.collectAsState()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Reage a mudanças no estado (Sucesso ou Erro)
    LaunchedEffect(authState.value) {
        when (val state = authState.value) {
            is Response.Success -> {
                // Navega para Home e remove a tela de Login da pilha (para não voltar ao clicar em Back)
                navController.navigate(Routes.Home.route) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
            }
            is Response.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo de volta!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Senha",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Entrar",
            onClick = { viewModel.login(email, password) },
            isLoading = authState.value is Response.Loading,
            enabled = email.isNotEmpty() && password.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Não tem conta? Cadastre-se",
            modifier = Modifier.clickable {
                navController.navigate(Routes.SignUp.route)
            },
            color = MaterialTheme.colorScheme.primary
        )
    }
}
/** Gemini final */