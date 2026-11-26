package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.repository.MusicRepository
import com.example.playlistmarket.domain.model.TrackList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map



class MusicInteractImpl (
    private val repository: MusicRepository,
) : MusicInteractor {
    private var isClickAllowed = true

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    override suspend fun searchMusic(expression: String): Flow<Result<TrackList>> =
        repository.searchMusic(expression).map { result ->
            Result.success(result)
        }.debounce(CLICK_DEBOUNCE_DELAY)
            .distinctUntilChanged()

}