package com.example.playlistmarket.domain.api

import com.example.playlistmarket.presentation.MediaPlayerMy

interface AudioInteractor {
    fun AudioSwitch (consumer: AudioConsumer)
    fun startPlay()
    fun stopPlay()
    fun prepare(media: MediaPlayerMy)
    interface AudioConsumer {
        fun consumer()
    }

}