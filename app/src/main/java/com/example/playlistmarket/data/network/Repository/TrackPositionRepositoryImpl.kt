package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.repository.TrackPositionRepository
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

class TrackPositionRepositoryImpl (
    private val trackPositionClient : TrackPositionClient
) : TrackPositionRepository {
    override fun saveTrackPosition(track: TrackPosition) {
        trackPositionClient.saveTrackPosition(
            TrackPositionSaveRequest(
                track.trackUrl,
                track.position
            )
        )
    }

    override fun loadTrackPosition(): TrackPosition {
        val response = trackPositionClient.loadTrackPosition()
        return TrackPosition(response.trackUrl,response.position)
    }
}