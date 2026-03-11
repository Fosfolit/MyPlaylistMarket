package com.example.playlistmarket.domain.lmpl

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.presentation.MediaPlayerMy
import com.example.playlistmarket.presentation.MediaPlayerMy.PlayerState

class AudioInteractorImpl (): AudioInteractor {
    private lateinit var medio: MediaPlayerMy
    private var play: Boolean =false
    private var tik :Runnable = Runnable{
        medio.setTimer()
        tak.run()
    }
    private var tak :Runnable = Runnable{
        handler.postDelayed(tik, 1000L)
    }
    private val handler = Handler(Looper.getMainLooper())
    override fun prepare(media: MediaPlayerMy){
        medio = media
        medio.preparePlayer()
    }

    override fun getTime() : Int {
        return medio.getTime()
    }

    override fun AudioSwitch() {
        medio.musicSwitch()
        if (play){
            play = true
            tik.run()
        }else{
            play = false
            handler.removeCallbacks(tik)
            medio.setTimer()
        }
    }

    override fun startPlay() {
     // val t = Thread {
            medio.startPlay()
            tik.run()
    // //  }
    // /   t.start()
    }

    override fun stopPlay() {
            handler.removeCallbacks(tik)
            medio.stopPlay()
            medio.setTimer()
    }


}