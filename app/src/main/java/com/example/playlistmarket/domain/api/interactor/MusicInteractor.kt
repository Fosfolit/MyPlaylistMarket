package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.TrackList
import kotlinx.coroutines.flow.Flow

interface MusicInteractor {
    suspend fun searchMusic(expression: String): Flow<Result<TrackList>>
}