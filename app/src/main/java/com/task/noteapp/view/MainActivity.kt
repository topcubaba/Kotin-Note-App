package com.task.noteapp.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.adapter.NoteAdapter
import com.task.noteapp.databinding.ActivityMainBinding
import com.task.noteapp.model.Note
import com.task.noteapp.viewModel.NoteViewModel
import com.task.noteapp.utils.SwipeMovement
import dagger.hilt.android.AndroidEntryPoint

//TODO: code refactoring

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NoteAdapter.NoteClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: NoteAdapter
    lateinit var viewModel: NoteViewModel
    lateinit var selectedNote: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val swipeMovement = object : SwipeMovement(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                selectedNote = adapter.notes[viewHolder.adapterPosition]
                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.delete(selectedNote)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeMovement)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as Note
                viewModel.insert(note)
            }
        }

        binding.fabAddNote.setOnClickListener {
            getContent.launch(Intent(this, AddNoteActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note") as Note
            viewModel.update(note)
        }
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }

}
