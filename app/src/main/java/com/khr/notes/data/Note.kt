package com.khr.notes.data

import kotlinx.coroutines.flow.MutableStateFlow

data class Note(
    var id: Int = 0,
    var title: String,
    var body: String
)