package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicRepository

class MusicInteractImpl(private val repository: MusicRepository) : MusicInteractor {


    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {
        val t = Thread {
            consumer.consume(repository.searchMusic(expression))
        }
        t.start()
    }
}