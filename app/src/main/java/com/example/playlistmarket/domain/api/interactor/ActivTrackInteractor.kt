package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.DataMusic

interface ActivTrackInteractor {
    suspend fun saveTrack(expression: DataMusic)
    suspend fun loadTrack(): DataMusic
}
