package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.TrackList

interface MusicRepository  {
    fun searchMusic(expression: String): TrackList
}