package com.example.playlistmarket

import android.content.Context
import com.example.playlistmarket.data.network.activTrack.ActivTrackRepositoryImpl
import com.example.playlistmarket.data.network.activTrack.StorageActivTrackClient
import com.example.playlistmarket.data.network.searchMusic.MusicRepositoryImpl
import com.example.playlistmarket.data.network.searchMusic.RetrofitNetworkClient
import com.example.playlistmarket.data.network.SharedPrefsStorageClient
import com.example.playlistmarket.data.network.StorageRepositoryImpl
import com.example.playlistmarket.data.network.theme.StorageThemeClient
import com.example.playlistmarket.data.network.theme.ThemeRepositoryImpl
import com.example.playlistmarket.domain.lmpl.ActivTrackInteractorImpl
import com.example.playlistmarket.domain.lmpl.MusicInteractImpl
import com.example.playlistmarket.domain.lmpl.StorageInteractImpl
import com.example.playlistmarket.domain.lmpl.ThemeInteractorImpl
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackRepository
import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicRepository
import com.example.playlistmarket.domain.api.StorageInteractor
import com.example.playlistmarket.domain.api.StorageRepository
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeRepository


object Creator {
    private fun getMusicRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMusicInteractor(): MusicInteractor {
        return MusicInteractImpl(getMusicRepository())
    }
    private fun getStorageRepository(context : Context): StorageRepository {
        return StorageRepositoryImpl(SharedPrefsStorageClient(context))
    }

    fun provideStorageInteractor(context : Context): StorageInteractor {
        return StorageInteractImpl(getStorageRepository(context))
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

}