package com.example.playlistmarket.data

import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest


interface StorageClient {
    fun saveTrackPosition(dto: TrackPositionSaveRequest)
    fun loadTrackPosition(): TrackPositionDto
}