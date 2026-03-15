package com.example.playlistmarket.domain.lmpl

import android.os.Handler
import android.os.HandlerThread
import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicRepository


class MusicInteractImpl(private val repository: MusicRepository) : MusicInteractor {
    private var isClickAllowed = true
    private val handler = Handler(
        HandlerThread("MyBackgroundThread").apply
        { start() }.looper
    )
    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {
        val t = Thread {
            if (clickDebounce()){
                searchDebounce(Runnable{consumer.consume(repository.searchMusic(expression))})
            }

        }
        t.start()
    }
    override fun clickDebounce(consume: MusicInteractor.BoolMusicConsumer) {
        val t = Thread {
            consume.consume( clickDebounce())
        }
        t.start()
    }

    fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, 2000L)
        }
        return current
    }
    private fun searchDebounce(run:Runnable) {
        handler.removeCallbacks(run)
        handler.postDelayed(run, 2000L)
    }
}
