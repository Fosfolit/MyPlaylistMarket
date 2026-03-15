package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.DataMusicDto

interface ActivTrackClient {
    fun saveTrack(track: DataMusicDto)
    fun loadTrack(): DataMusicDto
}