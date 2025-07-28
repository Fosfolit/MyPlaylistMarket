package com.example.playlistmarket.data.dto.response

import com.example.playlistmarket.data.dto.Response
import com.example.playlistmarket.data.dto.dto.DataMusicDto

class MusicSearchResponse(val expression: String,
                          val results: List<DataMusicDto>): Response()