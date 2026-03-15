package com.example.playlistmarket.data.dto.searchMusic.request

import com.example.playlistmarket.data.dto.searchMusic.Response
import com.example.playlistmarket.data.dto.dto.DataMusicDto

class MusicSearchResponse(val expression: String,
                          val results: List<DataMusicDto>): Response()