package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.DataMusic

interface ActivTrackInteractor {
    fun saveTrack(expression: DataMusic)
    fun loadTrack(trackConsumer : ActivTrackConsumer)
    interface ActivTrackConsumer {
        fun consume(savedTrack: DataMusic)
    }
}
