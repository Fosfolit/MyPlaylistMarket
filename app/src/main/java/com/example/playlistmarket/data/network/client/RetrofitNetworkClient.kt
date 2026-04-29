package com.example.playlistmarket.data.network.client

import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchRequest
import com.example.playlistmarket.data.dto.searchMusic.Response
import com.example.playlistmarket.data.interfaceClient.MusicInterface



class RetrofitNetworkClient (
    private val retrofit : MusicInterface
): NetworkClient {

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

