package com.example.playlistmarket.domain.api.interactor


interface ThemeInteractor {
    fun loadTheme(consumeTheme: ThemeConsumer)
    fun saveTheme(theme :Boolean)
    interface ThemeConsumer {
        fun consume(isDarkMode :Boolean)
    }
}