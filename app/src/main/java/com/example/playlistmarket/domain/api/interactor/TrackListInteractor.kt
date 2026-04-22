package com.example.playlistmarket.domain.api.interactor

import com.example.playlistmarket.domain.DataMusic
import java.util.LinkedList

interface TrackListInteractor {
    fun addItem(track :DataMusic)
    fun saveListTrack(list:LinkedList<DataMusic>)
    fun loadListTrack(consume: LoadTrackList)
    fun clearListTrack()

    interface LoadTrackList {
        fun consume(list: LinkedList<DataMusic>)
    }

}