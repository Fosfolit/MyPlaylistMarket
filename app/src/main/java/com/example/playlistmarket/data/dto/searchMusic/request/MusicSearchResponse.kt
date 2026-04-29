package com.example.playlistmarket.data.dto.searchMusic.request

import com.example.playlistmarket.data.dto.searchMusic.Response
import com.example.playlistmarket.data.dto.dto.DataMusicDto
import java.util.LinkedList

class MusicSearchResponse(val expression: String,
                          val results: LinkedList<DataMusicDto>
): Response()