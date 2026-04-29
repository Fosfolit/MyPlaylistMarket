package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.di.TrackPositionMapper
import com.example.playlistmarket.domain.model.TrackPosition
import com.example.playlistmarket.domain.api.repository.TrackPositionRepository

class TrackPositionRepositoryImpl (
    private val trackPositionClient : TrackPositionClient,
    private val mapper: TrackPositionMapper
) : TrackPositionRepository {
    override fun saveTrackPosition(track: TrackPosition) {
        trackPositionClient.saveTrackPosition(
            mapper.toDTO(track)
        )
    }

    override fun loadTrackPosition(): TrackPosition {
        return mapper.fromDTO(trackPositionClient.loadTrackPosition())
    }
}