package com.example.playlistmarket.domain.api

import com.example.playlistmarket.domain.DataMusic

interface MusicRepository {
    fun searchMusic(expression: String): List<DataMusic>
}