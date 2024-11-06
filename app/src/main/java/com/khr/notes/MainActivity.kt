package com.khr.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.khr.notes.data.NoteDAO
import com.khr.notes.ui.NotesViewModel
import com.khr.notes.ui.NotesViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        val mySQLNoteDAO = NoteDAO()
        val rdsUrl = "jdbc:mysql://notesdb.clooqg6amod9.us-east-1.rds.amazonaws.com:3306"
        lifecycleScope.launch(Dispatchers.IO) {
            mySQLNoteDAO.connect(rdsUrl,  getString(R.string.db_user), getString(R.string.db_password))
        }

        super.onCreate(savedInstanceState)

        val viewModel: NotesViewModel by viewModels { NotesViewModelFactory(mySQLNoteDAO) }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NotesApp(viewModel)
            }
        }
    }
}