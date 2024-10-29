package com.khr.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.khr.notes.data.NotesDB
import com.khr.notes.ui.NotesViewModel
import com.khr.notes.ui.NotesViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var database: NotesDB

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,
            NotesDB::class.java,
            "notes_database"
        ).build()
        super.onCreate(savedInstanceState)

        val viewModel: NotesViewModel by viewModels { NotesViewModelFactory(database) }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NotesApp(viewModel) // Pass the ViewModel to your composable function
            }
        }
    }
}