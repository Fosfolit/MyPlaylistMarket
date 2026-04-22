package com.example.playlistmarket.domain.api.repository

import android.os.Handler
import com.example.playlistmarket.domain.DataMusic

interface MusicRepository {
    val handler : Handler
    fun searchMusic(expression: String): List<DataMusic>
}