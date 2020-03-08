package com.notesapp.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.notesapp.ApiCalls.ApiService
import com.notesapp.ApiCalls.ApiUtils
import com.notesapp.Database.DBOpenHelper
import com.notesapp.Database.NotesModel
import com.notesapp.Model.Notes
import com.notesapp.R
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    lateinit var apiClientToken: ApiService
    var notesList:List<Notes>? = ArrayList()
    var notesListModel:List<NotesModel>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val dbHandler = DBOpenHelper(this@SplashActivity, null)

            // This method will be executed once the timer is over
            // Start your app main activity
            progressBar.visibility = View.VISIBLE
            apiClientToken = ApiUtils.APIService;
            val call: Call<List<Notes>> = apiClientToken.search()
            call.enqueue(object : Callback<List<Notes>> {
                override fun onFailure(call: Call<List<Notes>>, t: Throwable) {
                    Toast.makeText(this@SplashActivity,t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Notes>>, response: Response<List<Notes>>) {
                    if (response.isSuccessful){
                        notesList = response.body()
                        if (!notesList.isNullOrEmpty()) {
                            for (note in notesList!!) {
                                notesListModel = notesListModel?.plus(NotesModel(note.userId, note.id,
                                    note.title, "",false, note.completed))
                            }
                        }
                        if (!notesListModel.isNullOrEmpty()){
                            for (notes in notesListModel!!){
                                if (!dbHandler.checkAlreadyExist(notes.notesId))
                                    dbHandler.addNotes(notes)
                            }
                        }
                        val intent = Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        val intent = Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            })


            // close this activity
            finish()


    }
}
