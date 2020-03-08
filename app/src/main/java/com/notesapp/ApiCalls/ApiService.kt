package com.notesapp.ApiCalls

import com.notesapp.Model.Notes
import retrofit2.Call

interface ApiService {
    @retrofit2.http.GET("todos/")
    fun search() : Call<List<Notes>>
}