package com.example.playlistmarket

import com.google.gson.annotations.SerializedName

data class DataMusic(
    @SerializedName("trackName")val trackName: String, // Название композиции
    @SerializedName("artistName")val artistName: String, // Имя исполнителя
    @SerializedName("trackTimeMillis")val trackTime: Long, // Продолжительность трека
    @SerializedName("artworkUrl100")val artworkUrl100: String, // Ссылка на изображение обложки
    @SerializedName("collectionName")val collectionName: String,
    @SerializedName("releaseDate")val releaseDate: String,
    @SerializedName("primaryGenreName")val primaryGenreName: String,
    @SerializedName("country")val country: String
    )
data class ListDataMusic(
    @SerializedName("resultCount")val resultCount: Int,
    @SerializedName("results")val results: List<DataMusic>
)