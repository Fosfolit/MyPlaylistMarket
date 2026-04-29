package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.TrackPosition

interface TrackPositionRepository {
    fun saveTrackPosition(track: TrackPosition)
    fun loadTrackPosition(): TrackPosition
}