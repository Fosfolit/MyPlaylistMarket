package com.example.playlistmarket.domain.api.activTrack

import com.example.playlistmarket.domain.DataMusic

interface ActivTrackInteractor {
    fun saveTrack(expression: DataMusic)
    fun loadTrack(trackConsumer : ActivTrackConsumer)
    interface ActivTrackConsumer {
        fun consume(savedTrack: DataMusic)
    }
}
