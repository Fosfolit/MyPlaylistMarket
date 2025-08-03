package com.example.playlistmarket.data.network

import com.example.playlistmarket.data.interfaceClient.StorageClient
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.StorageRepository
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest
import com.example.playlistmarket.domain.DataMusic

class StorageRepositoryImpl(private val storageClient : StorageClient) :StorageRepository {
    override fun saveTrackPosition(track: TrackPosition) {
         storageClient.saveTrackPosition(
            TrackPositionSaveRequest(
            track.trackUrl,
            track.position
            )
        )
    }

    override fun loadTrackPosition(): TrackPosition {
        val response = storageClient.loadTrackPosition()
        return TrackPosition(response.trackUrl,response.position)
    }

    override fun saveTrack(track: DataMusic) {
        storageClient.saveTrack(  DataMusicDto(
            track.previewUrl,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country))
    }

    override fun loadTrack(): DataMusic {
       val out =  storageClient.loadTrack ()
        if (out == null){
            return DataMusic(
                "",
                "",
                "",
                0,
                "",
                "",
                "",
                "",
                ""
                )
        } else {
            return DataMusic(
                out.previewUrl,
                out.trackName,
                out.artistName,
                out.trackTime,
                out.artworkUrl100,
                out.collectionName,
                out.releaseDate,
                out.primaryGenreName,
                out.country)
        }
    }


}