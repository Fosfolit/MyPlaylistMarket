package com.example.playlistmarket.domain.api

import com.example.playlistmarket.presentation.MediaPlayerMy

interface AudioInteractor {
    fun AudioSwitch ()
    fun startPlay()
    fun stopPlay()
    fun prepare(media: MediaPlayerMy)
    fun getTime() :Int
}