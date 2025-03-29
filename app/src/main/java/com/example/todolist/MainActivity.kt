package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.todolist.ui.theme.ToDoListTheme

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el modo Edge-to-Edge para que la interfaz ocupe toda la pantalla
        enableEdgeToEdge()
        // Configura el contenido usando Jetpack Compose
        setContent {
            // Aplica el tema de la aplicación (Material3 personalizado)
            ToDoListTheme {
                // Surface es el contenedor base que usa el color de fondo definido en el tema
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Llama al composable principal de la aplicación
                    TodoApp()
                }
            }
        }
    }
}

