package com.example.playlistmarket.di

import com.example.playlistmarket.data.dto.dto.DataMusicDto
import com.example.playlistmarket.data.dto.dto.TrackListDto
import com.example.playlistmarket.data.dto.dto.TrackPositionDto
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.TrackList
import com.example.playlistmarket.domain.model.TrackPosition
import com.google.gson.Gson
import java.util.LinkedList

class DataMusicMapper {
    fun fromDTO(dto: DataMusicDto): DataMusic {
        return DataMusic(
            dto.previewUrl,
            dto.trackName,
            dto.artistName,
            dto.trackTime,
            dto.artworkUrl100,
            dto.collectionName,
            dto.releaseDate,
            dto.primaryGenreName,
            dto.country
        )
    }

    fun toDTO(user: DataMusic): DataMusicDto {
        return DataMusicDto(
            user.previewUrl,
            user.trackName,
            user.artistName,
            user.trackTime,
            user.artworkUrl100,
            user.collectionName,
            user.releaseDate,
            user.primaryGenreName,
            user.country
        )
    }
}

class TrackListMapper (
    private val mapper : DataMusicMapper
){
    fun fromDTO(dto: TrackListDto): TrackList {
        return TrackList(
            dto.trackSearchList.map {
                mapper.fromDTO(it)
            }.toCollection(LinkedList())
        )
    }
    fun toDTO(user: TrackList): TrackListDto {
        return TrackListDto(
            user.list.map {
                mapper.toDTO(it)
            }.toCollection(LinkedList())
        )
    }
}

class GsonMapper {
    fun toDTO(dto: Any): String {
        return Gson().toJson(dto)
    }
    fun <T> fromDTO(dto: String?, classOfT: Class<T>): T{
         return Gson().fromJson(dto, classOfT)
    }
}


class TrackPositionMapper {
    fun fromDTO(dto: TrackPositionDto): TrackPosition {
        return TrackPosition(
            dto.trackUrl,
            dto.position
        )
    }

    fun toDTO(user: TrackPosition): TrackPositionDto {
        return TrackPositionDto(
            user.trackUrl,
            user.position
        )
    }
}
