package com.example.playlistmarket.domain

import com.google.gson.annotations.SerializedName

data class DataMusic(
    @SerializedName("previewUrl")val previewUrl: String,
    @SerializedName("trackName")val trackName: String, // Название композиции
    @SerializedName("artistName")val artistName: String, // Имя исполнителя
    @SerializedName("trackTimeMillis")val trackTime: Long, // Продолжительность трека
    @SerializedName("artworkUrl100")val artworkUrl100: String, // Ссылка на изображение обложки
)
data class ListDataMusic(
    @SerializedName("resultCount")val resultCount: Int,
    @SerializedName("results")val results: List<DataMusic>

)