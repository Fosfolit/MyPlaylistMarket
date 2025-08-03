package com.example.playlistmarket

import android.content.Context
import com.example.playlistmarket.data.network.SearchMusic.MusicRepositoryImpl
import com.example.playlistmarket.data.network.SearchMusic.RetrofitNetworkClient
import com.example.playlistmarket.domain.Impl.MusicInteractImpl
import com.example.playlistmarket.domain.api.SearchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.SearchMusic.MusicRepository
import com.example.playlistmarket.domain.api.StorageInteractor
import com.example.playlistmarket.domain.api.StorageRepository
import com.example.playlistmarket.domain.Impl.StorageInteractImpl
import com.example.playlistmarket.data.network.StorageRepositoryImpl
import com.example.playlistmarket.data.network.SharedPrefsStorageClient
import com.example.playlistmarket.data.network.Theme.StorageThemeClient
import com.example.playlistmarket.data.network.Theme.ThemeRepositoryImpl
import com.example.playlistmarket.domain.Impl.ThemeInteractorImpl
import com.example.playlistmarket.domain.api.Theme.ThemeInteractor
import com.example.playlistmarket.domain.api.Theme.ThemeRepository

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

}