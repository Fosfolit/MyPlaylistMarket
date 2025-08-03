package com.example.playlistmarket.domain.Impl

import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.domain.api.StorageInteractor
import com.example.playlistmarket.presentation.MediaPlayer

//class AudioInteractorImpl(private val medio: MediaPlayer) : AudioInteractor {
//    override fun AudioSwitch(medio: MediaPlayer) {
//        medio.musicSwitch()
//    }
//
//    override fun startTimer(consumer : AudioInteractor.AudioConsumer) {
//        val t = Thread {
//            consumerTrack.consume(repository.loadTrack())
//        }
//        t.start()
//        timeFun = Runnable{timerQdt()}
//        handler.postDelayed(timeFun , 100L)
//    }
//
//    override fun stopTimer() {
//        TODO("Not yet implemented")
//    }
//    override fun loadTrack(consumerTrack: StorageInteractor.TrackConsumer) {
//        val t = Thread {
//            consumerTrack.consume(repository.loadTrack())
//        }
//        t.start()
//    }
//}