package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

interface StorageClient {
    fun saveTrackPosition(dto: TrackPositionSaveRequest)
    fun loadTrackPosition(): TrackPositionDto
    fun saveTrack(track: DataMusicDto)
    fun loadTrack(): DataMusicDto?
}