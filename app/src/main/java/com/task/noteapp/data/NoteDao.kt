package com.task.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.noteapp.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("UPDATE note_table SET title = :title, description = :description, image = :image, date = :date, isEdited = :isEdited WHERE id = :id")
    suspend fun update(id: Int?, title: String, description: String, image: String?, date: String, isEdited: Boolean = true)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}