package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackPositionDto



interface TrackPositionClient {
    suspend fun saveTrackPosition(dto: TrackPositionDto)
    suspend fun loadTrackPosition(): TrackPositionDto
}