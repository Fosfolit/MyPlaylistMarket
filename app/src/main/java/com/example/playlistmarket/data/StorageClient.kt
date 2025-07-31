package com.example.playlistmarket.data

import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

interface StorageClient {
    fun saveTrackPosition(dto: TrackPositionSaveRequest)
    fun loadTrackPosition(): TrackPositionDto
    fun loadTheme(): Boolean
    fun saveTheme(theme :Boolean)
    fun saveTrack(track: DataMusicDto)
    fun loadTrack(): DataMusicDto?
}