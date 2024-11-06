package com.khr.notes.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khr.notes.data.Note
import com.khr.notes.data.NoteDAO
import com.khr.notes.ui.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(viewModel: NotesViewModel, click: (Note)->Unit, note : Note, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
                    .combinedClickable(
                    onClick = { click(note)},
                    onLongClick = {
                        showDialog = true
                    }
                    )
                    .fillMaxWidth()
    ) {
        Text(
            text = note.title,
            modifier = modifier
                .absolutePadding(top = 12.dp, left = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = if(note.body.length > 75) {
                note.body.substring(0, 75) + "..."
            } else {
                note.body
            },
            modifier = modifier
                .absolutePadding(left = 20.dp,top = 5.dp,bottom = 10.dp),
            textAlign = TextAlign.Left
        )
    }
    if (showDialog) {
        val coroutineScope = rememberCoroutineScope()
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    // Delete the note using dao
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            viewModel.deleteNote(note)
                        }
                    }
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog= false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
fun AllNoteCards(viewModel: NotesViewModel, onClicked: (Note) -> Unit, modifier: Modifier = Modifier) {

    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(notes) { note->
            NoteCard(viewModel = viewModel, note = note , click = onClicked )
        }
    }
}
