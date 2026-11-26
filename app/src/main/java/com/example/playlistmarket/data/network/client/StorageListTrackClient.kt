package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackListDto
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.di.GsonMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.util.LinkedList


class StorageListTrackClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
) : TrackListClient {


    override suspend fun loadListTrack(nameType: String): TrackListDto =
        withContext(Dispatchers.IO) {
            if (sharedPrefs.contains(nameType)) {
                val json = sharedPrefs.getString(nameType, null)
                mapper.fromDTO(json, TrackListDto::class.java)
            } else {
                TrackListDto(LinkedList<DataMusicDto>())
            }
        }
    override suspend fun saveListTrack(nameType: String,list : TrackListDto) =
        withContext(NonCancellable +Dispatchers.IO) {
            sharedPrefs.edit()
                .remove(nameType)
                .putString(nameType, mapper.toDTO(list))
                .apply()
        }



}