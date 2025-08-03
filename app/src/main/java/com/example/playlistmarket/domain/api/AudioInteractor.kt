package com.example.playlistmarket.domain.api

import com.example.playlistmarket.domain.DataMusic

import com.example.playlistmarket.presentation.MediaPlayer

interface AudioInteractor {
    fun AudioSwitch (medio :MediaPlayer)
    fun startTimer()
    fun stopTimer()
    interface AudioConsumer {
        fun consumer()
    }

}