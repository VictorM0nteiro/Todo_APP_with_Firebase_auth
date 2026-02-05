package com.example.todoapp_firebase.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Gemini início
 *
 * Prompt: Crie um componente Composable reutilizável chamado CustomButton.
 * Ele deve receber: text (String), onClick (Lambda), modifier (Modifier), isLoading (Boolean) e enabled (Boolean).
 * Se isLoading for true, mostre um CircularProgressIndicator pequeno dentro do botão. Caso contrário, mostre o texto.
 * Use ButtonDefaults para estilizar com as cores do MaterialTheme.
 *
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.height(24.dp) // Ícone pequeno de loading
            )
        } else {
            Text(text = text)
        }
    }
}
/** Gemini final */