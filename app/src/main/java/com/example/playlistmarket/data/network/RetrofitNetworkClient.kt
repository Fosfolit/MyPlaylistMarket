package com.example.playlistmarket.data.network

import com.example.playlistmarket.data.NetworkClient
import com.example.playlistmarket.data.dto.MusicSearchRequest
import com.example.playlistmarket.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitNetworkClient : NetworkClient {


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<MusicInterface>()


    override fun doRequest(dto: Any): Response {
        if (dto is MusicSearchRequest) {
            val resp = retrofit.getMusic(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}