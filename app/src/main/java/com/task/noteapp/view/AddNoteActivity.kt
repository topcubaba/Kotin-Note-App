package com.task.noteapp.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.task.noteapp.R
import com.task.noteapp.databinding.ActivityAddNoteBinding
import com.task.noteapp.model.Note
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

//TODO: code refactoring
@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            isUpdate = true
            binding.etTitle.setText(old_note.title)
            binding.etDescription.setText(old_note.description)
            binding.etImageUrl.setText(old_note.image)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imgConfirm.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val image = if(binding.etImageUrl.text.toString() == "") null else binding.etImageUrl.text.toString()


            if (title.isNotEmpty() && description.isNotEmpty()){
                val formatter = SimpleDateFormat("dd/MM/yyyy")

                if(isUpdate){
                    note = Note(
                        old_note.id,
                        title,
                        description,
                        formatter.format(Date()),
                        image,
                        isEdited = true
                    )
                }   else{
                        note = Note(
                            null,
                            title,
                            description,
                            formatter.format(Date()),
                            image
                            )
                    }
                val intent = intent
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

    }
}