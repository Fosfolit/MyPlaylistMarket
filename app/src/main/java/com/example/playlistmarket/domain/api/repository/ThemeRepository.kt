package com.example.playlistmarket.domain.api.repository

interface ThemeRepository {
    fun loadTheme() :Boolean
    fun saveTheme(theme :Boolean)
}