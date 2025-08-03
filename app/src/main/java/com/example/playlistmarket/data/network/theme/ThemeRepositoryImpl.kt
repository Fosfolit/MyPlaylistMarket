package com.example.playlistmarket.data.network.theme

import com.example.playlistmarket.data.interfaceClient.ThemeClient
import com.example.playlistmarket.domain.api.theme.ThemeRepository

class ThemeRepositoryImpl(private val themeClient: ThemeClient): ThemeRepository {

    override fun loadTheme(): Boolean {
        return  themeClient.loadTheme()
    }

    override fun saveTheme(theme: Boolean) {
        themeClient.saveTheme(theme)
    }

}