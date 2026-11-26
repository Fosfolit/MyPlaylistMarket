package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.api.interactor.ActivTrackInteractor
import com.example.playlistmarket.domain.api.repository.ActivTrackRepository


class ActivTrackInteractorImpl (
    private val repository: ActivTrackRepository
): ActivTrackInteractor {
    override suspend fun saveTrack(expression: DataMusic) =
            repository.saveTrack(expression)


    override suspend fun loadTrack():DataMusic =
          repository.loadTrack()

}