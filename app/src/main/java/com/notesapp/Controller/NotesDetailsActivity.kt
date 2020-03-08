package com.notesapp.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notesapp.Database.DBOpenHelper
import com.notesapp.Database.NotesModel
import com.notesapp.R
import com.notesapp.Utilities.EXTRA_NOTES
import kotlinx.android.synthetic.main.activity_notes_details.*

class NotesDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_details)

        val notes: NotesModel? = intent.getParcelableExtra(EXTRA_NOTES)

        tvNotesId.text = notes?.notesId.toString()
        tvTitle.text = notes?.title

        val dbHandler = DBOpenHelper(this, null)

        if (dbHandler.checkIfFavorite(notes?.notesId)){
            ivFavorite.isSelected = true
            ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_filled))
        }
        else {
            ivFavorite.isSelected = false
            ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_unfilled))
        }

        val body = dbHandler.checkIfBody(notes?.notesId)
        if (body != null){
            etBody.setText(body)
        }
        ivFavorite.setOnClickListener {
            if (ivFavorite.isSelected){
                ivFavorite.isSelected = false
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_unfilled))
                dbHandler.updateFavoritesData(notes?.notesId,false)
            } else {
                ivFavorite.isSelected = true
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_fav_filled))
                dbHandler.updateFavoritesData(notes?.notesId,true)
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            if (etBody.text.length > 0) {
                dbHandler.updateBodyData(notes?.notesId, etBody.text.toString())
                finish()
            }
            else {
                etBody.error = "Please add body"
            }
        }
    }
}
