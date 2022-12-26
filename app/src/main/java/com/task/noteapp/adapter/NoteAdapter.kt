package com.task.noteapp.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import chooseColor
import com.task.noteapp.R
import com.task.noteapp.model.Note
import loadImage

//TODO code refactoring

class NoteAdapter(private val context : Context, val listener: NoteClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    val notes = ArrayList<Note>()
    private val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.note_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.title.text = currentNote.title
        holder.description.text = currentNote.description
        holder.date.text = currentNote.date
        currentNote.image?.let {
            holder.image.loadImage(currentNote.image)
        }
        if (currentNote.isEdited) {
            holder.isEdited.visibility = View.VISIBLE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.note_card.setCardBackgroundColor(context.resources.getColor(chooseColor(position), null))
        }

        holder.note_card.setOnClickListener {
            listener.onItemClicked(notes[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateList(newList: List<Note>) {
        notes.clear()
        notes.addAll(newList)
        allNotes.clear()
        allNotes.addAll(allNotes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note_card = itemView.findViewById<CardView>(R.id.note_card)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
        val image = itemView.findViewById<ImageView>(R.id.iv_image)
        val isEdited = itemView.findViewById<TextView>(R.id.tv_is_edited)
    }

    interface NoteClickListener {
        fun onItemClicked(note: Note)
    }
}
