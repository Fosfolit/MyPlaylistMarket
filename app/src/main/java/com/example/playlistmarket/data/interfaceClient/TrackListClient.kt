package com.example.playlistmarket.data.interfaceClient

import com.example.playlistmarket.data.dto.dto.TrackListDto

interface TrackListClient {
    fun saveListTrack(list: TrackListDto)
    fun loadListTrack(): TrackListDto

}