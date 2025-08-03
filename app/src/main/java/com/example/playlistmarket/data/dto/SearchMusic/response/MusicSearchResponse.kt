package com.example.playlistmarket.data.dto.SearchMusic.response

import com.example.playlistmarket.data.dto.SearchMusic.Response
import com.example.playlistmarket.data.dto.dto.DataMusicDto

class MusicSearchResponse(val expression: String,
                          val results: List<DataMusicDto>): Response()