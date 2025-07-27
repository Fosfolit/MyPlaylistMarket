package com.example.playlistmarket.data.dto

import com.google.gson.annotations.SerializedName

data class ListDataMusicDto(
    @SerializedName("resultCount")val resultCount: Int,
    @SerializedName("results")val results: List<DataMusicDto>
): Response()