package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.TrackList

interface TrackListInteractor {
    fun addItem(track : DataMusic)
    fun saveListTrack(list: TrackList)
    fun loadListTrack(consume: LoadTrackList)
    fun clearListTrack()

    interface LoadTrackList {
        fun consume(list: TrackList)
    }

}