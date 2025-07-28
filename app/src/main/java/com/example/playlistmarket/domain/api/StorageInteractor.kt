package com.example.playlistmarket.domain.api

import com.example.playlistmarket.domain.TrackPosition

interface StorageInteractor{
    fun saveTrackPosition(expression: TrackPosition)
    fun loadTrackPosition(consumer: StorageConsumer)

    interface StorageConsumer {
        fun consume(track: TrackPosition )
    }
}