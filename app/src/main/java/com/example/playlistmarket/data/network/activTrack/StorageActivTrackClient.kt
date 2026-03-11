package com.example.playlistmarket.data.network.activTrack

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmarket.Constants.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.google.gson.Gson

class StorageActivTrackClient(context: Context):ActivTrackClient{
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        PRACTICUM_EXAMPLE_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun saveTrack(track: DataMusicDto) {
        sharedPrefs.edit()
            .remove("TrackSave")
            .putString("TrackSave", Gson().toJson(track))
            .apply()
    }

    override fun loadTrack(): DataMusicDto {
        if(sharedPrefs.contains("TrackSave")) {
            val json =  sharedPrefs.getString("TrackSave", null)
            return Gson().fromJson(json, DataMusicDto::class.java)
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