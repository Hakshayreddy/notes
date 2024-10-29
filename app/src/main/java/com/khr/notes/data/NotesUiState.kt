package com.khr.notes.data

data class NotesUiState(
    val note: Note,
    val noteDao: NoteDAO
)