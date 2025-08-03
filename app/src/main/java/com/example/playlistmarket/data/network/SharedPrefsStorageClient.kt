package com.example.playlistmarket.data.network

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmarket.Constants.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmarket.data.interfaceClient.StorageClient
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest

import com.google.gson.Gson

class SharedPrefsStorageClient(context: Context) : StorageClient {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        PRACTICUM_EXAMPLE_PREFERENCES,
        Context.MODE_PRIVATE
    )

    override fun saveTrackPosition(dto: TrackPositionSaveRequest) {
            sharedPrefs.edit()
                .remove("TrackPosition")
                .putString("TrackPosition",
                    Gson().toJson(
                        TrackPositionDto(
                            dto.trackUrl,
                            dto.position
                        )
                    )
                )
                .apply()
    }

    override fun loadTrackPosition(): TrackPositionDto {
       if(sharedPrefs.contains("TrackPosition")){
            val track = sharedPrefs.getString("TrackPosition", null)

            return Gson().fromJson(track, TrackPositionDto ::class.java)
        }
        return TrackPositionDto("",0)
    }
}