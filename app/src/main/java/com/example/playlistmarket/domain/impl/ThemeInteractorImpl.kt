package com.example.playlistmarket.domain.impl

import com.example.playlistmarket.domain.api.interactor.ThemeInteractor
import com.example.playlistmarket.domain.api.repository.ThemeRepository

class ThemeInteractorImpl (
    private val repository: ThemeRepository
): ThemeInteractor {
    override suspend fun loadTheme():Boolean =
        repository.loadTheme()


    override suspend fun saveTheme(theme: Boolean) =
        repository.saveTheme(theme)

}