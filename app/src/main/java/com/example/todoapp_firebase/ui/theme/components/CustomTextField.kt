package com.example.todoapp_firebase.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/** Gemini início
 *
 * Prompt: Crie um componente Composable reutilizável chamado CustomTextField.
 * Ele deve receber: value (String), onValueChange (Lambda), label (String), isPassword (Boolean) e keyboardOptions.
 * Se isPassword for true, adicione um ícone de olho para mostrar/ocultar a senha e use PasswordVisualTransformation.
 * Use OutlinedTextField para o design.
 *
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPassword) {
            {
                val image = if (passwordVisible)
                    android.R.drawable.ic_menu_view // Usando ícone padrão do Android para simplificar
                else
                    android.R.drawable.ic_secure

                // Botão para alternar visibilidade
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    // Texto simples ou ícone caso queira importar vetores
                    Text(text = if (passwordVisible) "Ocultar" else "Ver")
                }
            }
        } else null
    )
}
/** Gemini final */