package com.task.noteapp.repository

import androidx.lifecycle.LiveData
import com.task.noteapp.data.NoteDao
import com.task.noteapp.model.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note.id, note.title, note.description, note.image, note.date, note.isEdited)
    }
}