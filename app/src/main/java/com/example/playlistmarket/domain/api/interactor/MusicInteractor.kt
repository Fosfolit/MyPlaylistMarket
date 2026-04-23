package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.TrackList

interface MusicInteractor {
    fun searchMusic(expression: String, consumer: MusicConsumer)
    fun clickDebounce(consume: BoolMusicConsumer)

    interface MusicConsumer {
        fun consume(foundMusicList: TrackList)
    }
    interface BoolMusicConsumer {
        fun consume(click: Boolean)
    }
}