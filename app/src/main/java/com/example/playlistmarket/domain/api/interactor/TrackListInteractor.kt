package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.TrackList

interface TrackListInteractor {
    suspend fun addItem(track : DataMusic)
    suspend fun saveListTrack(list: TrackList)
    suspend fun loadListTrack(): TrackList
    suspend fun clearListTrack()
}