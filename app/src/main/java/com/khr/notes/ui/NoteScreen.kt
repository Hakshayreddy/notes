package com.khr.notes.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khr.notes.NotesAppBar
import com.khr.notes.data.Note
import com.khr.notes.data.NoteDAO
import com.khr.notes.ui.theme.NotesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NotesViewModel, navigateUp : () -> Unit) {
    val note = viewModel.note
    var title by remember { mutableStateOf(note.title) }
    var body by remember { mutableStateOf(note.body) }
    val scope = rememberCoroutineScope()
    BackHandler {
        scope.launch {
            if(title.isNotEmpty() && body.isNotEmpty()) {
                if (note.id == 0) {
                    viewModel.insertNote(Note(title = title, body = body))
                } else {
                    viewModel.updateNote(Note(note.id, title, body))
                }
            }
            navigateUp()
        }}
    NotesTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                NotesAppBar(
                    currentScreen = "Notes",
                    canNavigateBack = true,
                    navigateUp = {
                        scope.launch {
                            if(title.isNotEmpty() && body.isNotEmpty()) {
                                if (note.id == 0) {
                                    viewModel.insertNote(Note(title = title, body = body))
                                } else {
                                    viewModel.updateNote(Note(note.id, title, body))
                                }
                            }
                            navigateUp()
                        }
                    },
                    modifier = Modifier
                )
            }) {
                innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .absolutePadding(left = 10.dp, right = 10.dp)
            ) {
                val rainbowColors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue)
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text("Title")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )

                )
                OutlinedTextField(
                    value = body,
                    onValueChange = {
                        body = it
                    },
                    label = {
                        Text("Notes....")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
        }
    }
}