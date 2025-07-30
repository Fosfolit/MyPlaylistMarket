package com.example.playlistmarket.domain.Impl

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

    override fun loadTheme(consumeTheme: StorageInteractor.ThemeConsumer) {
        val t = Thread {
            consumeTheme.consume(repository.loadTheme())
        }
        t.start()
    }

    override fun saveTheme(theme: Boolean) {
        val t = Thread {
            repository.saveTheme(theme)
        }
        t.start()
    }

}
