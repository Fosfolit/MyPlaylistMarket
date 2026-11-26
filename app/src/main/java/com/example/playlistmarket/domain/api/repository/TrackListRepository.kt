package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.TrackList

interface TrackListRepository {
    suspend fun saveListTrack(nameType: String,list: TrackList)
    suspend fun loadListTrack(nameType: String): TrackList
}