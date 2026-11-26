package com.example.playlistmarket.data.interfaceClient

interface ThemeClient {
    suspend fun loadTheme(): Boolean
    suspend fun saveTheme(theme :Boolean)
}