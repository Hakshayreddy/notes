package com.khr.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khr.notes.data.NotesDB

class NotesViewModelFactory(private val database: NotesDB) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}