package com.example.playlistmarket.domain.Impl

import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.StorageInteractor
import com.example.playlistmarket.domain.api.StorageRepository

class StorageInteractImpl(private val repository: StorageRepository): StorageInteractor {
    override fun saveTrackPosition(expression: TrackPosition) {
        val t = Thread {
           repository.saveTrackPosition(expression)
        }
        t.start()
    }

    override fun loadTrackPosition(consumer: StorageInteractor.StorageConsumer) {
        val t = Thread {
            consumer.consume(repository.loadTrackPosition())
        }
        t.start()
    }

    override fun saveTrack(expression: DataMusic) {
        val t = Thread {
            repository.saveTrack(expression)
        }
        t.start()
    }

    override fun loadTrack(consumerTrack: StorageInteractor.TrackConsumer) {
        val t = Thread {
            consumerTrack.consume(repository.loadTrack())
        }
        t.start()
    }

}
