package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.searchMusic.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}