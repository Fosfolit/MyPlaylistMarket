package com.example.playlistmarket.domain.api.activTrack

import com.example.playlistmarket.domain.DataMusic

interface ActivTrackRepository {
    fun saveTrack(track: DataMusic)
    fun loadTrack(): DataMusic
}