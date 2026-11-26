package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.TrackList

import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.api.repository.TrackListRepository
import java.util.LinkedList

class TrackListInteractorImpl (
    private val nameType: String,
    private val repository: TrackListRepository,
    private val maxListSize: Int
): TrackListInteractor {

    override suspend fun addItem(track: DataMusic) {
        val trackList : TrackList = repository.loadListTrack(nameType)
        trackList.list.remove(track)

        if (trackList.list.size >= maxListSize) {
            trackList.list.removeLast()
        }

        trackList.list.push(track)
        saveListTrack(trackList)
    }

    override suspend fun saveListTrack(list: TrackList) =
        repository.saveListTrack(nameType,list)


    override suspend fun loadListTrack(): TrackList =
        repository.loadListTrack(nameType)


    override suspend fun clearListTrack(){
        repository.saveListTrack(nameType,TrackList(LinkedList<DataMusic>()))
    }


}