package com.example.playlistmarket.data.dto.searchMusic.response

import com.example.playlistmarket.data.dto.searchMusic.Response
import com.example.playlistmarket.data.dto.dto.DataMusicDto

class MusicSearchResponse(val expression: String,
                          val results: List<DataMusicDto>): Response()