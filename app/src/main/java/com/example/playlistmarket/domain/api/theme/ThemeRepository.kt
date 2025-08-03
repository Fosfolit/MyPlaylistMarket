package com.example.playlistmarket.domain.api.theme

interface ThemeRepository {
    fun loadTheme() :Boolean
    fun saveTheme(theme :Boolean)
}