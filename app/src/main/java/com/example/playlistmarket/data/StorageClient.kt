package com.example.playlistmarket.data

import com.example.playlistmarket.data.dto.dto.TrackPositionDto


interface StorageClient {
    fun saveTrackPosition(dto: Any)
    fun loadTrackPosition(dto: Any?): TrackPositionDto
}