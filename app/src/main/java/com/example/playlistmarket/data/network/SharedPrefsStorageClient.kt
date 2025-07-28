package com.example.playlistmarket.data.network

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmarket.data.StorageClient
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.data.dto.request.TrackPositionLoadRequest
import com.example.playlistmarket.data.dto.request.TrackPositionSaveRequest
import com.example.playlistmarket.ui.PRACTICUM_EXAMPLE_PREFERENCES
import com.google.gson.Gson

class SharedPrefsStorageClient(context: Context) : StorageClient {
    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    override fun saveTrackPosition(dto: Any) {
        if (dto is TrackPositionSaveRequest) {
            sharedPrefs.edit()
                .remove("TracPosition")
                .putString("TracPosition",
                    Gson().toJson(
                        TrackPositionDto(
                            dto.trackUrl,
                            dto.position
                        )
                    ))
                .apply()
        }
    }

    override fun loadTrackPosition(dto: Any?): TrackPositionDto {
        if (dto is TrackPositionLoadRequest) {
            val track = sharedPrefs.getString("TracPosition", null)
            return Gson().fromJson(track, TrackPositionDto ::class.java)
        } else {
            return TrackPositionDto("",0)
        }
    }
}