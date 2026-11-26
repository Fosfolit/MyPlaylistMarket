package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.di.GsonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext


class SharedPrefsTrackPositionClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
) : TrackPositionClient {

    override suspend fun saveTrackPosition(dto: TrackPositionDto) =
        withContext(NonCancellable +Dispatchers.IO) {
            sharedPrefs.edit()
                .remove("TrackPosition")
                .putString("TrackPosition", mapper.toDTO(dto))
                .apply()
        }

    override suspend fun loadTrackPosition(): TrackPositionDto =
        withContext(Dispatchers.IO) {
            if(sharedPrefs.contains("TrackPosition")){
                val track = sharedPrefs.getString("TrackPosition", null)
                 mapper.fromDTO(track,TrackPositionDto::class.java)
            } else {
                TrackPositionDto("", 0)
            }
        }


}