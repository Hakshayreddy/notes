package com.khr.notes.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khr.notes.data.Note
import com.khr.notes.data.NoteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NotesViewModel(private val noteDAO: NoteDAO) : ViewModel() {

    private val _connectionStatus = MutableStateFlow(false)
    private val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()

    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    val allNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()
    var note = Note(0,"","")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteDAO.connectionState.collect { isConnected ->
                _connectionStatus.value = isConnected
                if (isConnected) {
                    _allNotes.value = noteDAO.getAllNotes().first()

                } else {
                    _allNotes.value = emptyList()
                }
            }
        }
    }

    fun currentNote(note : Note) {
        this.note = note
    }

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionStatus.value) {
                noteDAO.insert(note)
                _allNotes.value = noteDAO.getAllNotes().first()
            } else  {
                Log.e(TAG, "insertNote: database init failed")
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionStatus.value) {
                noteDAO.update(note)
                _allNotes.value = noteDAO.getAllNotes().first()
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionStatus.value) {
                noteDAO.delete(note)
                _allNotes.value = noteDAO.getAllNotes().first()
            }
        }
    }
}
