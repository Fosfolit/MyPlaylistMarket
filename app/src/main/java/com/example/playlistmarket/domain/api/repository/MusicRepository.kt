package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.TrackList
import kotlinx.coroutines.flow.Flow

interface MusicRepository  {
    fun searchMusic(expression: String): Flow<TrackList>
}