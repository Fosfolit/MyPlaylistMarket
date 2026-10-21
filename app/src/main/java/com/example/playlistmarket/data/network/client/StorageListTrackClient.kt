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


    override fun loadListTrack(nameType: String): TrackListDto {
        if(sharedPrefs.contains(nameType)) {
            val json =  sharedPrefs.getString(nameType, null)
            return mapper.fromDTO(json, TrackListDto::class.java)
        }
        return TrackListDto(LinkedList<DataMusicDto>())
    }
    override fun saveListTrack(nameType: String,list : TrackListDto) {
        sharedPrefs.edit()
            .remove(nameType)
            .putString(nameType,mapper.toDTO(list))
            .apply()
    }


}