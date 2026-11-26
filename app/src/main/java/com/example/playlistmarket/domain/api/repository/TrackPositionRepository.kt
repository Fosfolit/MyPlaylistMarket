package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.TrackPosition

interface TrackPositionRepository {
    suspend fun saveTrackPosition(track: TrackPosition)
    suspend fun loadTrackPosition(): TrackPosition
}