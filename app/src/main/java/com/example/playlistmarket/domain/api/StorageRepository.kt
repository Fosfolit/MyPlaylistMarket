package com.example.playlistmarket.domain.api

import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackPosition


interface StorageRepository {
    fun saveTrackPosition(track: TrackPosition)
    fun loadTrackPosition(): TrackPosition
    fun loadTheme() :Boolean
    fun saveTheme(theme :Boolean)
    fun saveTrack(track: DataMusic)
    fun loadTrack():  DataMusic
}
