package com.example.playlistmarket.domain.lmpl

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicRepository
import java.net.SocketTimeoutException


class MusicInteractImpl(private val repository: MusicRepository) : MusicInteractor {
    private var isClickAllowed = true
    private val handler = Handler(
        HandlerThread("MyBackgroundThread").apply
        { start() }.looper
    )
    private var lastSearchRunnable: Runnable? = null
    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {

        val newSearchRunnable = Runnable{
            try {
                val result = repository.searchMusic(expression)
                consumer.consume(result)
            } catch (e: Exception) {
                when (e) {
                    is NullPointerException -> {
                        Log.e("SearchError", "Null data: ${e.message}")
                        consumer.consume(emptyList())
                    }
                    else -> {
                        Log.e("SearchError", "Error: ${e.message}")
                        consumer.consume(emptyList())
                    }
                }
            }
        }
        val t = Thread {
            if (clickDebounce() || (newSearchRunnable != lastSearchRunnable)) {
                lastSearchRunnable?.let { handler.removeCallbacks(it) }
                lastSearchRunnable = newSearchRunnable
                handler.postDelayed(lastSearchRunnable!!, 2000L)
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
