package com.example.playlistmarket.domain.api.Theme

interface ThemeRepository {
    fun loadTheme() :Boolean
    fun saveTheme(theme :Boolean)
}