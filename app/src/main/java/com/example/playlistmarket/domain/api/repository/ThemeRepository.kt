package com.example.playlistmarket.domain.api.repository

interface ThemeRepository {
    suspend fun loadTheme() :Boolean
    suspend fun saveTheme(theme :Boolean)
}