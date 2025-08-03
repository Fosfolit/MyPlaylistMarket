package com.example.playlistmarket.domain.Impl

import com.example.playlistmarket.domain.api.Theme.ThemeInteractor
import com.example.playlistmarket.domain.api.Theme.ThemeRepository

class ThemeInteractorImpl(private val repository: ThemeRepository):ThemeInteractor {
    override fun loadTheme(consumeTheme: ThemeInteractor.ThemeConsumer) {
        val t = Thread {
            consumeTheme.consume(repository.loadTheme())
        }
        t.start()
    }

    override fun saveTheme(theme: Boolean) {
        val t = Thread {
            repository.saveTheme(theme)
        }
        t.start()
    }

}