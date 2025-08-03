package com.example.playlistmarket.data.network.SearchMusic

import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.dto.SearchMusic.request.MusicSearchRequest
import com.example.playlistmarket.data.dto.SearchMusic.response.MusicSearchResponse
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.SearchMusic.MusicRepository

class MusicRepositoryImpl(private val networkClient: NetworkClient) : MusicRepository {

    override fun searchMusic(expression: String): List<DataMusic> {
        val response = networkClient.doRequest(MusicSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as MusicSearchResponse).results.map {
                DataMusic(
                    it.previewUrl,
                    it.trackName,
                    it.artistName,
                    it.trackTime,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country) }
        } else {
            return emptyList()
        }
    }
}