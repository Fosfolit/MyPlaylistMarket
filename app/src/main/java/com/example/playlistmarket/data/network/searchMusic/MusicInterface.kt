package com.example.playlistmarket.data.network.searchMusic

import com.example.playlistmarket.data.dto.searchMusic.response.MusicSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicInterface {
    @GET("/search?entity=song ")
    fun getMusic(@Query("term", encoded = false) query: String): Call<MusicSearchResponse>

}
