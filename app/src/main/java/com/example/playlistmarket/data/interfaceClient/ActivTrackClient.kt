package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.DataMusicDto

interface ActivTrackClient {
    suspend fun saveTrack(track: DataMusicDto)
    suspend fun loadTrack(): DataMusicDto
}