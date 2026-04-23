package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackListDto
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.di.GsonMapper
import java.util.LinkedList


class StorageListTrackClient (
    private val sharedPrefs: SharedPreferences,
    private val mapper : GsonMapper
) : TrackListClient {


    override fun loadListTrack(): TrackListDto {
        if(sharedPrefs.contains("ListTrack")) {
            val json =  sharedPrefs.getString("ListTrack", null)
            return mapper.fromDTO(json, TrackListDto::class.java)
        }
        return TrackListDto(LinkedList<DataMusicDto>())
    }
    override fun saveListTrack(list : TrackListDto) {
        sharedPrefs.edit()
            .remove("ListTrack")
            .putString("ListTrack",mapper.toDTO(list))
            .apply()
    }
}