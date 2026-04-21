package com.example.playlistmarket.domain.impl

import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionRepository
import javax.inject.Inject

class TrackPositionInteractImpl @Inject constructor(
    private val repository: TrackPositionRepository
): TrackPositionInteractor {
    override fun saveTrackPosition(expression: TrackPosition) {
        val t = Thread {
            repository.saveTrackPosition(expression)
        }
        t.start()
    }

    override fun loadTrackPosition(consumer: TrackPositionInteractor.StorageConsumer) {
        val t = Thread {
            consumer.consume(repository.loadTrackPosition())
        }
        t.start()
    }
}