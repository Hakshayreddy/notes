package com.khr.notes.ui

import androidx.lifecycle.ViewModel
import com.khr.notes.data.Note
import com.khr.notes.data.NoteDAO
import com.khr.notes.data.NotesDB
import com.khr.notes.data.NotesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel(database: NotesDB) : ViewModel() {
    private val noteDao: NoteDAO = database.noteDao()

    private val _uiState = MutableStateFlow(NotesUiState(noteDao = noteDao, note = Note(title = "",body =  "")))
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun setNote(note: Note) {
        _uiState.update { currentState ->
            currentState.copy(note = note)
        }
    }
}