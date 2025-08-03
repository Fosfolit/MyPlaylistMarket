package com.example.playlistmarket.domain.api.SearchMusic

import com.example.playlistmarket.domain.DataMusic

interface MusicInteractor {
    fun searchMusic(expression: String, consumer: MusicConsumer)

    interface MusicConsumer {
        fun consume(foundMovies: List<DataMusic>)
    }
}