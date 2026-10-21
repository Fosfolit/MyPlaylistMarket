package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.di.TrackListMapper
import com.example.playlistmarket.domain.model.TrackList
import com.example.playlistmarket.domain.api.repository.TrackListRepository

class TrackListRepositoryImpl (
    private val client : TrackListClient,
    private val mapper : TrackListMapper
) : TrackListRepository {

    override fun saveListTrack(nameType: String,list: TrackList) {
        client.saveListTrack(nameType,mapper.toDTO(list))
    }

    override fun loadListTrack(nameType: String): TrackList {
        return mapper.fromDTO( client.loadListTrack(nameType))
    }

}