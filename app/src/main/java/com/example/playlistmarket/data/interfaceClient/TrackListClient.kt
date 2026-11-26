package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackListDto

interface TrackListClient {
    suspend fun saveListTrack(nameType: String,list: TrackListDto)
    suspend fun loadListTrack(nameType: String): TrackListDto
}