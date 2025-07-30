package com.example.playlistmarket.data.network

import com.example.playlistmarket.data.StorageClient
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.StorageRepository
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
        val response = storageClient.loadTrackPosition()
        return TrackPosition(response.trackUrl,response.position)
    }

    override fun loadTheme(): Boolean {
        return  storageClient.loadTheme()
    }

    override fun saveTheme(theme: Boolean) {
         storageClient.saveTheme(theme)
    }

}