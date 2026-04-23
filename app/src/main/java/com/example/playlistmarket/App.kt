package com.example.playlistmarket

import android.app.Application
import com.example.playlistmarket.di.clientHelpModule
import com.example.playlistmarket.di.clientModule
import com.example.playlistmarket.di.interactorModule
import com.example.playlistmarket.di.repositoryModule
import com.example.playlistmarket.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(clientModule,clientHelpModule,repositoryModule,interactorModule,viewModelModule)
        }
    }
}





