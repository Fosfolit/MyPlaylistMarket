package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.di.GsonMapper


class StorageActivTrackClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
):ActivTrackClient{

    override fun saveTrack(track: DataMusicDto) {
        sharedPrefs.edit()
            .remove("TrackSave")
            .putString("TrackSave", mapper.toDTO(track))
            .apply()
    }

    override fun loadTrack(): DataMusicDto {
        if(sharedPrefs.contains("TrackSave")) {
            val json =  sharedPrefs.getString("TrackSave", null)
            return mapper.fromDTO(json, DataMusicDto::class.java)
        } else {
            return DataMusicDto(
                "",
                "zer0",
                "zer0",
                0,
                "",
                "",
                "",
                "",
                ""
            )
        }
    }
}

