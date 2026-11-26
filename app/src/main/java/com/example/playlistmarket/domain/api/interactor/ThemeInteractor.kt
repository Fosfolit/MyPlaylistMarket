package com.example.playlistmarket.domain.api.interactor


interface ThemeInteractor {
    suspend fun loadTheme():Boolean
    suspend fun saveTheme(theme :Boolean)

}