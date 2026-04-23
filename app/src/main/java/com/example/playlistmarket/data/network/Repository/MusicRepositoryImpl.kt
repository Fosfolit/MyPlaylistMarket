package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchRequest
import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchResponse
import com.example.playlistmarket.di.DataMusicMapper
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackList
import com.example.playlistmarket.domain.api.repository.MusicRepository
import java.util.LinkedList


class MusicRepositoryImpl (
    private val networkClient: NetworkClient,
    private val mapper : DataMusicMapper
) : MusicRepository {


    override fun searchMusic(expression: String): TrackList {
        val response = networkClient.doRequest(MusicSearchRequest(expression))
        if (response.resultCode == 200) {
            val result = (response as MusicSearchResponse).results
            return TrackList(result.mapTo(LinkedList()){
                mapper.fromDTO(it)
            })
        } else {
            return TrackList(LinkedList<DataMusic>())
        }
    }
}

