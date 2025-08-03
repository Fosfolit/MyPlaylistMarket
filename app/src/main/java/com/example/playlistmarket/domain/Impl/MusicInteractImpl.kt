package com.example.playlistmarket.domain.Impl

import com.example.playlistmarket.domain.api.SearchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.SearchMusic.MusicRepository

class MusicInteractImpl(private val repository: MusicRepository) : MusicInteractor {


    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {
        val t = Thread {
            consumer.consume(repository.searchMusic(expression))
        }
        t.start()
    }
}