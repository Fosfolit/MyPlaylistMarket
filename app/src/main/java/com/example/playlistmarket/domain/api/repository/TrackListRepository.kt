package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.model.TrackList

interface TrackListRepository {
    fun saveListTrack(nameType: String,list: TrackList)
    fun loadListTrack(nameType: String): TrackList
}