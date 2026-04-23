package com.example.playlistmarket.domain.lmpl

import android.os.Handler
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackList
import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.repository.MusicRepository
import java.util.LinkedList


class MusicInteractImpl (
    private val repository: MusicRepository,
    private val handler : Handler
) : MusicInteractor {
    private var isClickAllowed = true
    private var lastSearchRunnable: Runnable? = null
    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {

        val newSearchRunnable = Runnable{
            try {
                val result = repository.searchMusic(expression)
                consumer.consume(result)
            } catch (e: Exception) {
                when (e) {
                    is NullPointerException -> {
                       consumer.consume(TrackList(LinkedList<DataMusic>()))
                    }
                    else -> {
                         consumer.consume(TrackList(LinkedList<DataMusic>()))
                    }
                }
            }
        }
        val t = Thread {
            if (clickDebounce() || (newSearchRunnable != lastSearchRunnable)) {
                lastSearchRunnable?.let { handler.removeCallbacks(it) }
                lastSearchRunnable = newSearchRunnable
                searchDebounce(lastSearchRunnable!!)
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
