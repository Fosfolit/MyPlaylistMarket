package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.TrackList

interface TrackListRepository {
    fun saveListTrack(list:TrackList)
    fun loadListTrack(): TrackList
}