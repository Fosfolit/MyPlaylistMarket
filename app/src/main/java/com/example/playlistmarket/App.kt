package com.example.playlistmarket

import android.app.Application
import android.media.MediaPlayer
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideMusicInteractor
import com.example.playlistmarket.Creator.provideStorageInteractor
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.Creator.provideTrackListInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor

class App : Application() {

    companion object {
        @Volatile
        private var instance: App? = null
        fun getInstance(): App {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }


    lateinit var trackListInteractor: TrackListInteractor
        private set
    lateinit var trackPositionInteractor: TrackPositionInteractor
        private set
    lateinit var activTrack : ActivTrackInteractor
        private set
    lateinit var mediaPlayer : MediaPlayer
        private set
    lateinit var themeInteractor : ThemeInteractor
        private set
    lateinit var musicInteractor: MusicInteractor
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        trackListInteractor = provideTrackListInteractor(this)
        trackPositionInteractor = provideStorageInteractor(this)
        activTrack = provideActivTrackInteractor(this)
        mediaPlayer = MediaPlayer()
        themeInteractor = provideThemeInteractor(this)
        musicInteractor = provideMusicInteractor()
    }
}