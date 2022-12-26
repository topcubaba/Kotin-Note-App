package com.task.noteapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val description: String,
    val date: String,
    val image: String? = null,
    val isEdited: Boolean = false
) : java.io.Serializable
