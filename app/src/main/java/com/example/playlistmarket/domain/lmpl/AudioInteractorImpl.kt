package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.presentation.MediaPlayerMy

class AudioInteractorImpl (): AudioInteractor {
    private lateinit var medio: MediaPlayerMy

    override fun prepare(media: MediaPlayerMy){
        medio = media
    }

    override fun AudioSwitch(consumer: AudioInteractor.AudioConsumer) {
        val t = Thread {
            medio.musicSwitch()
        }
    }

    override fun startPlay() {
        val t = Thread {
            medio.startPlay()
        }
    }

    override fun stopPlay() {
        val t = Thread {
            medio.stopPlay()
        }
    }

}