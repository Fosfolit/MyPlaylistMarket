package com.example.playlistmarket

import android.content.Context
import com.example.playlistmarket.data.network.activTrack.ActivTrackRepositoryImpl
import com.example.playlistmarket.data.network.activTrack.StorageActivTrackClient
import com.example.playlistmarket.data.network.searchMusic.MusicRepositoryImpl
import com.example.playlistmarket.data.network.searchMusic.RetrofitNetworkClient
import com.example.playlistmarket.data.network.trackPosition.SharedPrefsTrackPositionClient
import com.example.playlistmarket.data.network.trackPosition.TrackPositionRepositoryImpl
import com.example.playlistmarket.data.network.theme.StorageThemeClient
import com.example.playlistmarket.data.network.theme.ThemeRepositoryImpl
import com.example.playlistmarket.data.network.trackList.StorageListTrackClient
import com.example.playlistmarket.data.network.trackList.TrackListRepositoryImpl
import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.domain.lmpl.ActivTrackInteractorImpl
import com.example.playlistmarket.domain.lmpl.MusicInteractImpl
import com.example.playlistmarket.domain.lmpl.TrackPositionInteractImpl
import com.example.playlistmarket.domain.lmpl.ThemeInteractorImpl
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackRepository
import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicRepository
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionRepository
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeRepository
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListRepository
import com.example.playlistmarket.domain.lmpl.AudioInteractorImpl
import com.example.playlistmarket.domain.lmpl.TrackListInteractorImpl


object Creator {
    private fun getMusicRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMusicInteractor(): MusicInteractor {
        return MusicInteractImpl(getMusicRepository())
    }
    private fun getStorageRepository(context : Context): TrackPositionRepository {
        return TrackPositionRepositoryImpl(SharedPrefsTrackPositionClient(context))
    }

    fun provideStorageInteractor(context : Context): TrackPositionInteractor {
        return TrackPositionInteractImpl(getStorageRepository(context))
    }

    private fun getThemeRepository(context : Context): ThemeRepository {
        return ThemeRepositoryImpl(StorageThemeClient(context))
    }

    fun provideThemeInteractor(context : Context): ThemeInteractor {
        return ThemeInteractorImpl(getThemeRepository(context))
    }

    private fun getActivTrackRepository(context : Context): ActivTrackRepository {
        return ActivTrackRepositoryImpl(StorageActivTrackClient(context))
    }

    fun provideActivTrackInteractor(context : Context): ActivTrackInteractor {
        return ActivTrackInteractorImpl(getActivTrackRepository(context))
    }

    private fun getTrackListRepository(context : Context): TrackListRepository {
        return TrackListRepositoryImpl(StorageListTrackClient(context))
    }

    fun provideTrackListInteractor(context : Context): TrackListInteractor {
        return TrackListInteractorImpl(getTrackListRepository(context))
    }

    fun provideAudioInteractor(): AudioInteractor {
        return AudioInteractorImpl()
    }

}