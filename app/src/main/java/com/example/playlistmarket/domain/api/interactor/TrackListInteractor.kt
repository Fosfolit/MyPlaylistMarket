package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackList
import java.util.LinkedList

interface TrackListInteractor {
    fun addItem(track :DataMusic)
    fun saveListTrack(list: TrackList)
    fun loadListTrack(consume: LoadTrackList)
    fun clearListTrack()

    interface LoadTrackList {
        fun consume(list: TrackList)
    }

}