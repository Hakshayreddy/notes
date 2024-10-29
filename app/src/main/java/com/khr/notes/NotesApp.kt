package com.khr.notes

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khr.notes.ui.HomeScreen
import com.khr.notes.ui.NoteScreen
import com.khr.notes.ui.NotesViewModel


enum class NotesScreen() {
    Home,
    Notes
}

@Composable
fun NotesApp(
    viewModel: NotesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = NotesScreen.Home.name,
        modifier = Modifier
    ) {
        composable(route = NotesScreen.Home.name) {
            HomeScreen(
                noteDao = uiState.noteDao,
                onNotesClicked = {note->
                    viewModel.setNote(note)
                    navController.navigate(NotesScreen.Notes.name)
                },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = NotesScreen.Notes.name) {
            NoteScreen(
                note = uiState.note,
                navigateUp = { navController.navigateUp() },
                dao = uiState.noteDao
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(
            text = currentScreen,
            style = MaterialTheme.typography.displayLarge,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic

        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .absolutePadding(top = 10.dp,bottom = 10.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}