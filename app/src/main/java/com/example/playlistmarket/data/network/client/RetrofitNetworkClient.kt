package com.example.playlistmarket.data.network.client

import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchRequest
import com.example.playlistmarket.data.dto.searchMusic.Response
import com.example.playlistmarket.data.interfaceClient.MusicInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RetrofitNetworkClient (
    private val retrofit : MusicInterface
): NetworkClient {

     override suspend fun doRequest(dto: Any): Response {
         return if (dto is MusicSearchRequest) {
             withContext(Dispatchers.IO) {
                 try {
                     val response = retrofit.getMusic(dto.expression)
                     response.apply { resultCode = 200 }
                 } catch (e: Throwable) {
                     Response().apply { resultCode = 500 }
                 }
             }
         } else {
             Response().apply { resultCode = 400 }
         }
    }
}

