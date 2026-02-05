package com.example.todoapp_firebase.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp_firebase.ui.components.CustomTextField

/** Gemini início
 *
 * Prompt: Crie um Composable AddTaskDialog que exibe um AlertDialog.
 * Ele deve receber onDismiss (Lambda) e onConfirm (Lambda que recebe título e descrição).
 * Use dois CustomTextFields para título e descrição.
 * O botão "Adicionar" deve estar desabilitado se o título estiver vazio.
 *
 */
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Nova Tarefa") },
        text = {
            Column {
                CustomTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Título"
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Descrição (Opcional)"
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(title, description) },
                enabled = title.isNotBlank()
            ) {
                Text(text = "Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        }
    )
}
/** Gemini final */