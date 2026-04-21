package com.example.playlistmarket.data.network.searchMusic

import android.os.Handler
import android.os.HandlerThread
import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchRequest
import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.dto.searchMusic.request.MusicSearchResponse
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.searchMisuc.MusicRepository
import javax.inject.Inject


class MusicRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : MusicRepository {



   override val handler = Handler(
        HandlerThread("MyBackgroundThread").apply
        { start() }.looper
    )

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
                    it.country
                )
            }
        } else {
            return emptyList()
        }
    }
}

