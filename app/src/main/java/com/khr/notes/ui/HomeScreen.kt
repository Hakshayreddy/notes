package com.khr.notes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.khr.notes.NotesAppBar
import com.khr.notes.R
import com.khr.notes.data.Note
import com.khr.notes.data.NoteDAO
import com.khr.notes.ui.components.AllNoteCards
import com.khr.notes.ui.theme.NotesTheme

@Composable
fun HomeScreen(viewModel: NotesViewModel, onNotesClicked: (Note) -> Unit, navigateUp : () -> Unit) {
    NotesTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                NotesAppBar(
                    currentScreen = stringResource(R.string.home_name),
                    canNavigateBack = false,
                    navigateUp = navigateUp,
                    modifier = Modifier
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNotesClicked(Note(title = "",body = "")) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }) {
                innerPadding ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(innerPadding)
                        .absolutePadding(left = 10.dp, right = 10.dp)
                ) {
                    AllNoteCards(
                        viewModel = viewModel,
                        onClicked = onNotesClicked
                    )
                }

        }
    }
}
