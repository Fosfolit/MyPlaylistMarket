package com.example.playlistmarket.domain.Impl

import com.example.playlistmarket.domain.api.MusicInteractor
import com.example.playlistmarket.domain.api.MusicRepository

class MusicInteractorImpl(private val repository: MusicRepository) : MusicInteractor {


    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {
        val t = Thread {
            consumer.consume(repository.searchMusic(expression))
        }
        t.start()
    }
}