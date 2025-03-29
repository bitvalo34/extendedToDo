package com.example.todolist

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogProperties

// Composable que muestra un diálogo para editar una tarea existente
@Composable
fun EditTaskDialog(
    task: Task,              // Tarea que se desea editar
    onDismiss: () -> Unit,   // Función que se invoca cuando se descarta el diálogo sin guardar cambios
    onSave: (String) -> Unit // Función que se invoca al guardar el cambio, pasando el nuevo texto de la tarea
) {
    // Estado local para almacenar el texto editado; se inicializa con el texto actual de la tarea
    var editedText by remember { mutableStateOf(task.text) }

    // Se utiliza un AlertDialog de Material3 para la edición
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Editar Tarea") },
        text = {
            // Campo de texto para modificar la descripción de la tarea
            TextField(
                value = editedText,
                onValueChange = { editedText = it },
                label = { Text("Tarea") }
            )
        },
        confirmButton = {
            // Botón para confirmar y guardar los cambios
            Button(
                onClick = {
                    if (editedText.isNotBlank()) {
                        onSave(editedText)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            // Botón para cancelar la edición
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        },
        properties = DialogProperties()
    )
}

