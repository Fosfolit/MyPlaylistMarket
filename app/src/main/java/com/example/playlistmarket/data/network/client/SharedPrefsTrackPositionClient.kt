package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.di.GsonMapper


class SharedPrefsTrackPositionClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
) : TrackPositionClient {

    override fun saveTrackPosition(dto: TrackPositionDto) {
        sharedPrefs.edit()
            .remove("TrackPosition")
            .putString("TrackPosition", mapper.toDTO(dto))
            .apply()
    }
    override fun loadTrackPosition(): TrackPositionDto {
        if(sharedPrefs.contains("TrackPosition")){
            val track = sharedPrefs.getString("TrackPosition", null)
            return mapper.fromDTO(track,TrackPositionDto::class.java)
        }
        return TrackPositionDto("",0)
    }

}