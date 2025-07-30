package com.example.playlistmarket.domain.api

import com.example.playlistmarket.domain.TrackPosition

interface StorageInteractor{
    fun saveTrackPosition(expression: TrackPosition)
    fun loadTrackPosition(consumer: StorageConsumer)
    fun loadTheme(consumeTheme:ThemeConsumer )
    fun saveTheme(theme :Boolean)

    interface StorageConsumer {
        fun consume(track: TrackPosition )
    }
    interface ThemeConsumer {
        fun consume(theme :Boolean)
    }
}