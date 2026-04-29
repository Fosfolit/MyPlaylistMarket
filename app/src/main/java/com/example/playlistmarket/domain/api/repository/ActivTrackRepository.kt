package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.DataMusic

interface ActivTrackRepository {
    fun saveTrack(track: DataMusic)
    fun loadTrack(): DataMusic
}