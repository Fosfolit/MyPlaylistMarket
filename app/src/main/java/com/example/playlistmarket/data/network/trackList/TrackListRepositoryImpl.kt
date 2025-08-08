package com.example.playlistmarket.data.network.trackList

import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackListDto
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.trackList.TrackListRepository
import java.util.LinkedList

class TrackListRepositoryImpl(private val client : TrackListClient) : TrackListRepository {
    override fun saveListTrack(list: LinkedList<DataMusic>) {
        client.saveListTrack(
            TrackListDto(
                list.map {
                    DataMusicDto(
                        it.previewUrl,
                        it.trackName,
                        it.artistName,
                        it.trackTime,
                        it.artworkUrl100,
                        it.collectionName,
                        it.releaseDate,
                        it.primaryGenreName,
                        it.country
                    )
                }.toCollection(LinkedList())
            )
        )
    }

    override fun loadListTrack(): LinkedList<DataMusic> {

        return client.loadListTrack().trackSearchList.map { DataMusic(
            it.previewUrl,
            it.trackName,
            it.artistName,
            it.trackTime,
            it.artworkUrl100,
            it.collectionName,
            it.releaseDate,
            it.primaryGenreName,
            it.country) }.toCollection(LinkedList())
    }

}

