package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.TrackList

interface MusicInteractor {
    fun searchMusic(expression: String, consumer: MusicConsumer)
    fun clickDebounce(consume: BoolMusicConsumer)

    interface MusicConsumer {
        fun consume(foundMusicList: Result<TrackList>)
    }
    interface BoolMusicConsumer {
        fun consume(click: Boolean)
    }
}