package com.example.playlistmarket.data.network.activTrack

import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.activTrack.ActivTrackRepository

class ActivTrackRepositoryImpl(private val client : ActivTrackClient):ActivTrackRepository {
    override fun saveTrack(track: DataMusic) {
        client.saveTrack(
            DataMusicDto(
                track.previewUrl,
                track.trackName,
                track.artistName,
                track.trackTime,
                track.artworkUrl100,
                track.collectionName,
                track.releaseDate,
                track.primaryGenreName,
                track.country)
        )
    }

    override fun loadTrack(): DataMusic {
        val out =client.loadTrack()
        return DataMusic(
            out.previewUrl,
            out.trackName,
            out.artistName,
            out.trackTime,
            out.artworkUrl100,
            out.collectionName,
            out.releaseDate,
            out.primaryGenreName,
            out.country
        )
    }
}