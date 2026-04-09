package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicRepository


class MusicInteractImpl(private val repository: MusicRepository) : MusicInteractor {
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
                       consumer.consume(emptyList())
                    }
                    else -> {
                         consumer.consume(emptyList())
                    }
                }
            }
        }
        val t = Thread {
            if (clickDebounce() || (newSearchRunnable != lastSearchRunnable)) {
                lastSearchRunnable?.let { repository.handler.removeCallbacks(it) }
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
            repository.handler.postDelayed({ isClickAllowed = true }, 2000L)
        }
        return current
    }

    private fun searchDebounce(run:Runnable) {
        repository.handler.removeCallbacks(run)
        repository.handler.postDelayed(run, 2000L)
    }
}
