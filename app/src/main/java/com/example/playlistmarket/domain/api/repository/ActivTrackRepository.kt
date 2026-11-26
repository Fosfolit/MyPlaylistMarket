package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.DataMusic

interface ActivTrackRepository {
    suspend fun saveTrack(track: DataMusic)
    suspend fun loadTrack(): DataMusic
}