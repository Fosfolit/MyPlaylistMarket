package com.example.playlistmarket.domain.model

import com.google.gson.annotations.SerializedName

data class DataMusic(
    @SerializedName("previewUrl")val previewUrl: String,
    @SerializedName("trackName")val trackName: String,
    @SerializedName("artistName")val artistName: String,
    @SerializedName("trackTimeMillis")val trackTime: Long,
    @SerializedName("artworkUrl100")val artworkUrl100: String,
    @SerializedName("collectionName")val collectionName: String,
    @SerializedName("releaseDate")val releaseDate: String,
    @SerializedName("primaryGenreName")val primaryGenreName: String,
    @SerializedName("country")val country: String
)
