package com.example.playlistmarket.data.dto.response

import com.example.playlistmarket.data.dto.Response
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.domain.TrackPosition

class TrackPositionLoadResponce (
    val results: TrackPosition
): Response()