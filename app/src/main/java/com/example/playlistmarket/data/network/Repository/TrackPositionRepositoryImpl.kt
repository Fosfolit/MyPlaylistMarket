package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.di.TrackPositionMapper
import com.example.playlistmarket.domain.model.TrackPosition
import com.example.playlistmarket.domain.api.repository.TrackPositionRepository

class TrackPositionRepositoryImpl (
    private val trackPositionClient : TrackPositionClient,
    private val mapper: TrackPositionMapper
) : TrackPositionRepository {
    override suspend fun saveTrackPosition(track: TrackPosition) {
        trackPositionClient.saveTrackPosition(
            mapper
                .toDTO(track)
        )
    }

    override suspend fun loadTrackPosition(): TrackPosition {
        return mapper.fromDTO(
            trackPositionClient
                .loadTrackPosition()
        )
    }
}