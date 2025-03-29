package com.example.todolist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// Composable que representa cada elemento (tarea) en la lista
@Composable
fun TaskItem(
    task: Task,                      // Objeto de tipo Task que contiene la información de la tarea
    onDelete: () -> Unit,            // Función a ejecutar para borrar la tarea
    onEdit: () -> Unit,              // Función a ejecutar para editar la tarea (se invoca al pulsar el elemento)
    onImageSelected: (Uri) -> Unit   // Función a ejecutar al seleccionar una nueva imagen para la tarea
) {
    // Lanzador para seleccionar una imagen desde el dispositivo
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onImageSelected(it) }
    }

    // Tarjeta que contiene la visualización de la tarea
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onEdit() }, // Al hacer clic se abre el diálogo de edición
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Layout horizontal para mostrar la imagen, el texto y los botones
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Si la tarea tiene una imagen asignada, se muestra en forma circular
            if (task.imageUri != null) {
                AsyncImage(
                    model = task.imageUri,
                    contentDescription = "Imagen de la tarea",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Se muestra el texto de la tarea
            Text(text = task.text, modifier = Modifier.weight(1f))
            // Botón para borrar la tarea
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar tarea"
                )
            }
            // Botón para agregar o cambiar la imagen de la tarea
            IconButton(onClick = { launcher.launch("image/*") }) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Agregar/Cambiar imagen"
                )
            }
        }
    }
}

