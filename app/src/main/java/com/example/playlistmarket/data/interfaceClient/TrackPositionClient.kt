package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import javax.inject.Inject


interface TrackPositionClient {
    fun saveTrackPosition(dto: TrackPositionDto)
    fun loadTrackPosition(): TrackPositionDto
}