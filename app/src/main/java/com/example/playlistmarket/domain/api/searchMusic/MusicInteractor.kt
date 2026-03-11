package com.example.playlistmarket.domain.api.searchMusic

import com.example.playlistmarket.domain.DataMusic

interface MusicInteractor {
    fun searchMusic(expression: String, consumer: MusicConsumer)
    fun clickDebounce(consume:BoolMusicConsumer)
    interface MusicConsumer {
        fun consume(foundMovies: List<DataMusic>)
    }
    interface BoolMusicConsumer {
        fun consume(click: Boolean)
    }
}