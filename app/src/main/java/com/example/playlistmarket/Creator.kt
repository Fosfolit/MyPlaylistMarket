package com.example.playlistmarket

import com.example.playlistmarket.data.network.MusicRepositoryImpl
import com.example.playlistmarket.data.network.RetrofitNetworkClient
import com.example.playlistmarket.domain.Impl.MusicInteractorImpl
import com.example.playlistmarket.domain.api.MusicInteractor
import com.example.playlistmarket.domain.api.MusicRepository

object Creator {
    private fun getMusicRepository(): MusicRepository {
        return MusicRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMusicInteractor(): MusicInteractor {
        return MusicInteractorImpl(getMusicRepository())
    }
}