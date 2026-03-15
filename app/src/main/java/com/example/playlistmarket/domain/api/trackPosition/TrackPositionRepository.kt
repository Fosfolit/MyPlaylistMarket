package com.example.playlistmarket.domain.api.trackPosition

import com.example.playlistmarket.domain.TrackPosition

interface TrackPositionRepository {
    fun saveTrackPosition(track: TrackPosition)
    fun loadTrackPosition(): TrackPosition
}