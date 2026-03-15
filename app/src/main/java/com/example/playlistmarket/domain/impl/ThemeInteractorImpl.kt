package com.example.playlistmarket.domain.impl

import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeRepository

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