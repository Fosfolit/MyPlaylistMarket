package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackPositionDto

interface TrackPositionClient {
    fun saveTrackPosition(dto: TrackPositionDto)
    fun loadTrackPosition(): TrackPositionDto
}