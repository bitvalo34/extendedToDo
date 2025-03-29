package com.example.todolist

import android.net.Uri

// Clase de datos que representa una tarea en la lista de Todo
data class Task(
    val id: Int,         // Identificador único para cada tarea
    val text: String,    // Texto o descripción de la tarea
    val imageUri: Uri?   // URI de la imagen asociada a la tarea (puede ser null)
)

