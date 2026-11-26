package com.example.playlistmarket.domain.impl

import com.example.playlistmarket.domain.model.TrackPosition
import com.example.playlistmarket.domain.api.interactor.TrackPositionInteractor
import com.example.playlistmarket.domain.api.repository.TrackPositionRepository


class TrackPositionInteractImpl (
    private val repository: TrackPositionRepository
): TrackPositionInteractor {

    override suspend fun saveTrackPosition(expression: TrackPosition) =
        repository.saveTrackPosition(expression)


    override suspend fun loadTrackPosition(): TrackPosition =
        repository.loadTrackPosition()

}