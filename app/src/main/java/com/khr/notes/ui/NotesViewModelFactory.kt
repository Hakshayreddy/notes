package com.khr.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khr.notes.data.NoteDAO

class NotesViewModelFactory(private val noteDAO: NoteDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(noteDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}