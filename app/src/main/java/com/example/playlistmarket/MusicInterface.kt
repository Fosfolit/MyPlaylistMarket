package com.example.playlistmarket

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicInterface {
    @GET("/search?entity=song ")
    fun getMusic(@Query("term", encoded = false) query: String): Call<ListDataMusic>

}
