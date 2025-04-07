package com.example.newspaperapp.repository

import com.example.newspaperapp.data_model.Note

interface INoteRepository {
    suspend fun insertNote(note: Note): Long
    suspend fun getAllNotes(): List<Note>
    suspend fun updateNote(note: Note): Int
    suspend fun deleteNote(id: Int): Int
}