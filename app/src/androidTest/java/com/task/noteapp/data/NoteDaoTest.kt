package com.task.noteapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.task.noteapp.getOrAwaitValue
import com.task.noteapp.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.*

class NoteDaoTest {

    lateinit var noteDatabase : NoteDatabase
    lateinit var noteDao : NoteDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        noteDao = noteDatabase.noteDao()
    }

    @Test
    fun insertNote_expectedSingleNote() = runBlocking{
        val note = Note(1, "test title", "description", "date")
        noteDao.insert(note)
        val notes = noteDao.getAllNotes().getOrAwaitValue()
        Assert.assertEquals(1, notes.size)
        Assert.assertEquals("test title", notes[0].title)
    }

    @Test
    fun deleteNote_expectedEmptyList() = runBlocking{
        val note = Note(1, "test title", "description", "date")
        noteDao.insert(note)
        noteDao.delete(note)
        val notes = noteDao.getAllNotes().getOrAwaitValue()
        Assert.assertEquals(0, notes.size)
    }

    @Test
    fun updateNote_expectedUpdatedNote() = runBlocking{
        val note = Note(1, "test title", "description", "date")
        noteDao.insert(note)
        noteDao.update(1, "updated title", "updated description",
            "updated date", "date")
        val notes = noteDao.getAllNotes().getOrAwaitValue()
        Assert.assertEquals("updated title", notes[0].title)
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }
}