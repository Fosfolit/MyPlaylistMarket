package com.example.playlistmarket.domain.api.trackPosition

import com.example.playlistmarket.domain.TrackPosition

interface TrackPositionInteractor {
    fun saveTrackPosition(expression: TrackPosition)
    fun loadTrackPosition(consumer: StorageConsumer)
    interface StorageConsumer {
        fun consume(track: TrackPosition)
    }
}