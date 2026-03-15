package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.searchMusic.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}