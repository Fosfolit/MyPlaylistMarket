package com.example.playlistmarket.data.interfaceClient

interface PlaylistListClient {
    fun loadPlaylistList(): Boolean
    fun savePlaylistList(it :Boolean)
}