package com.example.playlistmarket.domain.api.repository

import com.example.playlistmarket.domain.DataMusic
import java.util.LinkedList

interface TrackListRepository {
    fun saveListTrack(list:LinkedList<DataMusic>)
    fun loadListTrack(): LinkedList<DataMusic>
}