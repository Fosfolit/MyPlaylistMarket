package com.example.playlistmarket.presentation

import android.media.MediaPlayer
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.TrackPosition
import com.google.android.material.button.MaterialButton

class MediaPlayer( val track : TrackPosition, val buttonPause: MaterialButton) {
    private var mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.STATE_DEFAULT
    enum class PlayerState  {
        STATE_DEFAULT ,
        STATE_PREPARED ,
        STATE_PLAYING ,
        STATE_PAUSED
    }//Виды состояния медиа плеера
    init{
        preparePlayer()
    }
    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            buttonPause.isEnabled = true
            playerState = PlayerState.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = PlayerState.STATE_PREPARED
        }

    }// Функция подготовки проигрывателя
    private fun stopPlay(){
        playerState = PlayerState.STATE_PAUSED
        buttonPause.setIconResource(R.drawable.button_play)
        mediaPlayer.pause()
    }
    private fun startPlay(){
        playerState = PlayerState.STATE_PLAYING
        buttonPause.setIconResource(R.drawable.button_pause)
        mediaPlayer.start()
    }
    fun musicSwitch () {
        when(playerState) {
            PlayerState.STATE_PLAYING -> {
                stopPlay()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                startPlay()
            }
            else ->{}
        }
    }

}