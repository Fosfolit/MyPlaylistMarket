package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

interface TrackPositionClient {
    fun saveTrackPosition(dto: TrackPositionSaveRequest)
    fun loadTrackPosition(): TrackPositionDto
}