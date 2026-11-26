package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.TrackPosition

interface TrackPositionInteractor {
    suspend fun saveTrackPosition(expression: TrackPosition)
    suspend fun loadTrackPosition(): TrackPosition
}