package com.notesapp.ApiCalls

import com.notesapp.ApiCalls.RetrofitClient.getClient
import com.notesapp.Utilities.BASE_URL

object ApiUtils {

    val APIService: ApiService
        get() = getClient(BASE_URL)!!.create(ApiService::class.java)
}