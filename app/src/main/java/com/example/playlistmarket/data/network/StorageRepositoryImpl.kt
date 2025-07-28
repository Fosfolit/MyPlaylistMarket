package com.example.playlistmarket.data.network

import com.example.playlistmarket.data.StorageClient
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.StorageRepository
import com.example.playlistmarket.data.dto.request.TrackPositionLoadRequest
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

class StorageRepositoryImpl(private val storageClient : StorageClient) :StorageRepository {
    override fun saveTrackPosition(track: TrackPosition) {
         storageClient.saveTrackPosition(
            TrackPositionSaveRequest(
            track.trackUrl,
            track.position
            )
        )
    }

    override fun loadTrackPosition(): TrackPosition {
        val response = storageClient.loadTrackPosition(TrackPositionLoadRequest())
        return TrackPosition(response.trackUrl,response.position)
    }

}