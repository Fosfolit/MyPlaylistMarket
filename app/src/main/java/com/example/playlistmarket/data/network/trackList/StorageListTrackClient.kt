package com.example.playlistmarket.data.network.trackList

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmarket.Constants.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackListDto
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.google.gson.Gson
import java.util.LinkedList

class StorageListTrackClient(context: Context) : TrackListClient {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        PRACTICUM_EXAMPLE_PREFERENCES,
        Context.MODE_PRIVATE
    )


    override fun loadListTrack(): TrackListDto {
        if(sharedPrefs.contains("ListTrack")) {
            val json =  sharedPrefs.getString("ListTrack", null)
            return Gson().fromJson(json, TrackListDto::class.java)
        }
        return TrackListDto(LinkedList<DataMusicDto>())
    }
    override fun saveListTrack(list : TrackListDto) {
        sharedPrefs.edit()
            .remove("ListTrack")
            .putString("ListTrack", Gson().toJson(list))
            .apply()
    }
}
