package com.notesapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notesapp.Database.NotesModel
import com.notesapp.R

class AllNotesAdapter(val context: Context, val notesList: List<NotesModel>?, val itemClick: (NotesModel?) -> Unit): RecyclerView.Adapter<AllNotesAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View, val itemClick: (NotesModel) -> Unit) : RecyclerView.ViewHolder(itemView){
        val notesTitle = itemView.findViewById<TextView>(R.id.tvNotesTitle)

        fun BindCategory(notes: NotesModel?, context: Context){
            notesTitle?.text = notes?.title

            itemView.setOnClickListener { itemClick(notes) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_list_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        if (notesList != null) {
            return notesList.count()
        }
        return 0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.BindCategory(notesList?.get(position), context)
    }

}