package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.di.GsonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext


class StorageActivTrackClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
):ActivTrackClient{

    override suspend fun saveTrack(track: DataMusicDto) =
        withContext(NonCancellable +Dispatchers.IO) {
            sharedPrefs.edit()
                .remove("TrackSave")
                .putString("TrackSave", mapper.toDTO(track))
                .apply()
        }


    override suspend fun loadTrack(): DataMusicDto =
        withContext(Dispatchers.IO) {
            if (sharedPrefs.contains("TrackSave")) {
                val json = sharedPrefs.getString("TrackSave", null)
                mapper.fromDTO(json, DataMusicDto::class.java)
            } else {
                DataMusicDto(
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

