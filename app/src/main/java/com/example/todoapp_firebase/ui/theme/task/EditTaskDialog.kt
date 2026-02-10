package com.example.todoapp_firebase.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoapp_firebase.data.model.Task
import com.example.todoapp_firebase.ui.components.CustomTextField

/**
 * EditTaskDialog - Diálogo para editar tarefas existentes
 *
 * Permite alterar título e descrição de uma tarefa
 */
@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Editar Tarefa",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Título"
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Descrição (Opcional)"
                )

                if (title.isBlank() && title.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "O título não pode estar vazio",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(title.trim(), description.trim())
                    }
                },
                enabled = title.isNotBlank(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Salvar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Cancelar")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}