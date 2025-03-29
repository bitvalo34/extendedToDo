package com.example.todolist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto

// Composable principal de la aplicación que gestiona la lista de tareas y la creación de nuevas tareas
@Composable
fun TodoApp() {
    // Lista mutable que contiene las tareas
    val tasks = remember { mutableStateListOf<Task>() }
    // Estado para el texto de la nueva tarea
    var newTaskText by remember { mutableStateOf("") }
    // Estado para la URI de la imagen seleccionada para la nueva tarea (puede ser null)
    var newTaskImageUri by remember { mutableStateOf<Uri?>(null) }
    // Estado que guarda la tarea que se está editando actualmente (null si no se está editando ninguna)
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    // Lanzador para seleccionar una imagen para la nueva tarea
    val newTaskImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { newTaskImageUri = it }
    }

    Column {
        // Sección para agregar una nueva tarea (incluye texto, selección de imagen y botón de agregar)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Campo de texto para escribir la descripción de la nueva tarea
                TextField(
                    value = newTaskText,
                    onValueChange = { newTaskText = it },
                    label = { Text("Nueva Tarea") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Botón para seleccionar una imagen del dispositivo
                    Button(onClick = { newTaskImageLauncher.launch("image/*") }) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Seleccionar imagen"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Seleccionar imagen")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Previsualización de la imagen seleccionada, si existe
                    newTaskImageUri?.let { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                    }
                    // Espaciador para alinear el botón de agregar tarea a la derecha
                    Spacer(modifier = Modifier.weight(1f))
                    // Botón para agregar la nueva tarea a la lista
                    Button(onClick = {
                        // Se valida que el campo de texto no esté vacío
                        if (newTaskText.isNotBlank()) {
                            // Se agrega la nueva tarea a la lista con la imagen seleccionada (o null si no se eligió)
                            tasks.add(Task(id = tasks.size, text = newTaskText, imageUri = newTaskImageUri))
                            // Se reinician los estados del texto y la imagen para la siguiente entrada
                            newTaskText = ""
                            newTaskImageUri = null
                        }
                    }) {
                        Text("Agregar tarea")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Sección para mostrar la lista de tareas existentes
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(tasks) { task ->
                // Cada tarea se muestra usando el composable TaskItem
                TaskItem(
                    task = task,
                    onDelete = { tasks.remove(task) },
                    onEdit = { taskToEdit = task },
                    onImageSelected = { uri ->
                        // Actualiza la imagen de la tarea seleccionada
                        val index = tasks.indexOf(task)
                        if (index >= 0) {
                            tasks[index] = task.copy(imageUri = uri)
                        }
                    }
                )
            }
        }
    }

    // Diálogo para editar la tarea seleccionada
    if (taskToEdit != null) {
        EditTaskDialog(
            task = taskToEdit!!,
            onDismiss = { taskToEdit = null },
            onSave = { updatedText ->
                // Actualiza el texto de la tarea editada
                val index = tasks.indexOf(taskToEdit)
                if (index >= 0) {
                    tasks[index] = taskToEdit!!.copy(text = updatedText)
                }
                // Cierra el diálogo de edición
                taskToEdit = null
            }
        )
    }
}

