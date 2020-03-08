package com.notesapp.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.notesapp.Adapters.AllNotesAdapter
import com.notesapp.Database.DBOpenHelper
import com.notesapp.Database.NotesModel
import com.notesapp.R
import com.notesapp.Utilities.EXTRA_NOTES
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var allNotesAdapter: AllNotesAdapter
    var notesList: List<NotesModel>? = ArrayList<NotesModel>()
    var favList: List<NotesModel>? = ArrayList<NotesModel>()
    val dbHandler = DBOpenHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        notesList = dbHandler.getAllNotes()
        if (!notesList.isNullOrEmpty()) {
            rvNotes.visibility = View.VISIBLE
            noNotes.visibility = View.GONE
            allNotesAdapter =
                AllNotesAdapter(this@MainActivity, notesList) { notes: NotesModel? ->
                    val intent = Intent(this@MainActivity, NotesDetailsActivity::class.java)
                    intent.putExtra(EXTRA_NOTES, notes)
                    startActivity(intent)
                }
            rvNotes.adapter = allNotesAdapter

            val layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvNotes.layoutManager = layoutManager
            rvNotes.setHasFixedSize(true)
        } else {
            rvNotes.visibility = View.GONE
            noNotes.visibility = View.VISIBLE
        }

    }

    @Override
    override fun onResume(){
        super.onResume()
        favList = dbHandler.getAllFavoriteNotes()
        if (!favList.isNullOrEmpty()) {
            rvFavorites.visibility = View.VISIBLE
            noFavorites.visibility = View.GONE

            allNotesAdapter =
                AllNotesAdapter(this@MainActivity, favList) { notes: NotesModel? ->
                    val intent = Intent(this@MainActivity, NotesDetailsActivity::class.java)
                    intent.putExtra(EXTRA_NOTES, notes)
                    startActivity(intent)
                }
            rvFavorites.adapter = allNotesAdapter

            val layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvFavorites.layoutManager = layoutManager
            rvFavorites.setHasFixedSize(true)

        } else {
            rvFavorites.visibility = View.GONE
            noFavorites.visibility = View.VISIBLE

        }
    }
}
