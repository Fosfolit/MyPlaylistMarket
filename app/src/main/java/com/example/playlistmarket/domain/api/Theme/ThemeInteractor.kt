package com.example.playlistmarket.domain.api.Theme


interface ThemeInteractor {
    fun loadTheme(consumeTheme: ThemeConsumer)
    fun saveTheme(theme :Boolean)
    interface ThemeConsumer {
        fun consume(theme :Boolean)
    }
}