package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.TrackPosition

interface TrackPositionRepository {
    fun saveTrackPosition(track: TrackPosition)
    fun loadTrackPosition(): TrackPosition
}