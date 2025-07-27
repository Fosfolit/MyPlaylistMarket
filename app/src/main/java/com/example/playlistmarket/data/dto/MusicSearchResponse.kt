package com.example.playlistmarket.data.dto

class MusicSearchResponse(val expression: String,
                          val results: List<DataMusicDto>): Response()