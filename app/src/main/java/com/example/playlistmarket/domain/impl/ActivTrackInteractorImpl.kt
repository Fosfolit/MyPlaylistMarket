package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackRepository
import javax.inject.Inject

class ActivTrackInteractorImpl @Inject constructor(
    private val repository: ActivTrackRepository
):ActivTrackInteractor {
    override fun saveTrack(expression: DataMusic) {
        val t = Thread {
            repository.saveTrack(expression)
        }
        t.start()
    }

    override fun loadTrack(trackConsumer: ActivTrackInteractor.ActivTrackConsumer) {
        val t = Thread {
            trackConsumer.consume(repository.loadTrack())
        }
        t.start()
    }
}