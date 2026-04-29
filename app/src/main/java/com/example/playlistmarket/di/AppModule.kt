package com.example.playlistmarket.di

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread
import com.example.playlistmarket.Constants.BASE_URL
import com.example.playlistmarket.Constants.HISTORY_COUNT_LIST
import com.example.playlistmarket.Constants.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.data.interfaceClient.MusicInterface
import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.interfaceClient.ThemeClient
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.data.network.Repository.ActivTrackRepositoryImpl
import com.example.playlistmarket.data.network.Repository.MusicRepositoryImpl
import com.example.playlistmarket.data.network.Repository.ThemeRepositoryImpl
import com.example.playlistmarket.data.network.Repository.TrackListRepositoryImpl
import com.example.playlistmarket.data.network.Repository.TrackPositionRepositoryImpl
import com.example.playlistmarket.data.network.client.RetrofitNetworkClient
import com.example.playlistmarket.data.network.client.SharedPrefsTrackPositionClient
import com.example.playlistmarket.data.network.client.StorageActivTrackClient
import com.example.playlistmarket.data.network.client.StorageListTrackClient
import com.example.playlistmarket.data.network.client.StorageThemeClient
import com.example.playlistmarket.domain.api.interactor.ActivTrackInteractor
import com.example.playlistmarket.domain.api.repository.ActivTrackRepository
import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.repository.MusicRepository
import com.example.playlistmarket.domain.api.interactor.ThemeInteractor
import com.example.playlistmarket.domain.api.repository.ThemeRepository
import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.api.repository.TrackListRepository
import com.example.playlistmarket.domain.api.interactor.TrackPositionInteractor
import com.example.playlistmarket.domain.api.repository.TrackPositionRepository
import com.example.playlistmarket.domain.impl.ThemeInteractorImpl
import com.example.playlistmarket.domain.impl.TrackPositionInteractImpl
import com.example.playlistmarket.domain.lmpl.ActivTrackInteractorImpl
import com.example.playlistmarket.domain.lmpl.MusicInteractImpl
import com.example.playlistmarket.domain.lmpl.TrackListInteractorImpl
import com.example.playlistmarket.ui.viewModel.AudioPlayerViewModel
import com.example.playlistmarket.ui.viewModel.MainViewModel
import com.example.playlistmarket.ui.viewModel.SearchViewModel
import com.example.playlistmarket.ui.viewModel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


val clientHelpModule = module {
    factory {
        androidContext().getSharedPreferences(
            PRACTICUM_EXAMPLE_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
    factory {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<MusicInterface>()
    }

    factory {
        Handler(
            HandlerThread("MyBackgroundThread").apply
            { start() }.looper
        )
    }

    factory { DataMusicMapper() }

    factory { TrackListMapper(get()) }

    factory { TrackPositionMapper() }

    factory { GsonMapper() }
}

val repositoryHelpModule = module {
    factory<ActivTrackRepository> {
        ActivTrackRepositoryImpl(get(),get())
    }
    factory<MusicRepository> {
        MusicRepositoryImpl(get(),get())
    }
    factory<ThemeRepository> {
        ThemeRepositoryImpl(get())
    }
    factory<TrackListRepository> {
        TrackListRepositoryImpl(get(),get())
    }
    factory<TrackPositionRepository> {
        TrackPositionRepositoryImpl(get(),get())
    }
}



val clientModule = module {
    factory<NetworkClient> {
        RetrofitNetworkClient(get())
    }
    factory<TrackPositionClient> {
        SharedPrefsTrackPositionClient(get(),get())
    }
    factory<ActivTrackClient> {
        StorageActivTrackClient(get(),get())
    }
    factory<TrackListClient> {
        StorageListTrackClient(get(),get())
    }
    factory<ThemeClient> {
        StorageThemeClient(get())
    }
}


val repositoryModule = module {
    factory<ActivTrackRepository> {
        ActivTrackRepositoryImpl(get(),get())
    }
    factory<MusicRepository> {
        MusicRepositoryImpl(get(),get())
    }
    factory<ThemeRepository> {
        ThemeRepositoryImpl(get())
    }
    factory<TrackListRepository> {
        TrackListRepositoryImpl(get(),get())
    }
    factory<TrackPositionRepository> {
        TrackPositionRepositoryImpl(get(),get())
    }
}


val interactorModule = module {
    single<ActivTrackInteractor> {
        ActivTrackInteractorImpl(get())
    }
    single<MusicInteractor> {
        MusicInteractImpl(get(),get())
    }
    single<ThemeInteractor> {
        ThemeInteractorImpl(get())
    }
    single<TrackListInteractor> {
        TrackListInteractorImpl(get(),HISTORY_COUNT_LIST)
    }
    single<TrackPositionInteractor> {
        TrackPositionInteractImpl(get())
    }
    single {
        MediaPlayer()
    }
}


val viewModelModule = module {
    viewModel {
        AudioPlayerViewModel(get(),get(),get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        SearchViewModel(get(),get(),get())
    }
    viewModel {
        SettingsViewModel(get())
    }
}