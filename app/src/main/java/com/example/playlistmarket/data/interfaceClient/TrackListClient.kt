package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackListDto

interface TrackListClient {
    fun saveListTrack(nameType: String,list: TrackListDto)
    fun loadListTrack(nameType: String): TrackListDto
}