package com.example.playlistmarket

import android.content.Context
import com.example.playlistmarket.data.network.MusicRepositoryImpl
import com.example.playlistmarket.data.network.RetrofitNetworkClient
import com.example.playlistmarket.domain.Impl.MusicInteractImpl
import com.example.playlistmarket.domain.api.MusicInteractor
import com.example.playlistmarket.domain.api.MusicRepository
import com.example.playlistmarket.domain.api.StorageInteractor
import com.example.playlistmarket.domain.api.StorageRepository
import com.example.playlistmarket.domain.Impl.StorageInteractImpl
import com.example.playlistmarket.data.network.StorageRepositoryImpl
import com.example.playlistmarket.data.network.SharedPrefsStorageClient

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
}