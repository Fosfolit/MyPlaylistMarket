package com.example.playlistmarket.data.network.Repository

import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.di.DataMusicMapper
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.api.repository.ActivTrackRepository

class ActivTrackRepositoryImpl (
    private val client : ActivTrackClient,
    private val mapper : DataMusicMapper
): ActivTrackRepository {

    override fun saveTrack(track: DataMusic) {
        client.saveTrack(mapper.toDTO(track))
    }

    override fun loadTrack(): DataMusic {
        return mapper.fromDTO(client.loadTrack())
    }

}
