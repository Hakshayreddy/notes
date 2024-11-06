package com.khr.notes.data

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class NoteDAO {

    private lateinit var connection: Connection

    private val _connectionState = MutableSharedFlow<Boolean>()
    val connectionState = _connectionState.asSharedFlow()

    suspend fun connect(url: String, user: String, pass: String) {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(url, user, pass)
            val statement = connection.createStatement()
            statement.execute("USE notes")
            _connectionState.emit(true)
        } catch (e: SQLException) {
            e.printStackTrace()
            _connectionState.emit(false)
            Log.d(TAG,"CANNOT CONNECTTTTT!!!!!!!!!!!!!!")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            _connectionState.emit(false)
            Log.d(TAG,"CANNOT CONNECTTTTT!!!!!!!!!!!!!!")
        }
    }

    suspend fun insert(note: Note) {
        try {
            val statement = connection.prepareStatement("INSERT INTO notes (title, content) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)
            statement.setString(1, note.title)
            statement.setString(2, note.body)
            statement.executeUpdate()

            val generatedKeys = statement.generatedKeys
            if (generatedKeys.next()) {
                note.id = generatedKeys.getInt(1) // Assuming 'id' is your primary key column
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    suspend fun update(note: Note) {
        try {
            val statement = connection.prepareStatement("UPDATE notes SET title = ?, content = ? WHERE id = ?")
            statement.setString(1, note.title)
            statement.setString(2, note.body)
            statement.setInt(3, note.id)
            statement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    suspend fun delete(note: Note) {
        try {
            val statement = connection.prepareStatement("DELETE FROM notes WHERE id = ?")
            statement.setInt(1, note.id)
            statement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    suspend fun getAllNotes(): Flow<List<Note>> = flow {
        try {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM notes")
            val notes = mutableListOf<Note>()
            while (resultSet.next()) {
                val note = Note(
                    id = resultSet.getInt("id"),
                    title = resultSet.getString("title"),
                    body = resultSet.getString("content")
                )
                notes.add(note)
            }
            emit(notes)
        } catch (e: SQLException) {
            e.printStackTrace()
            emit(emptyList())
        }
    }
}