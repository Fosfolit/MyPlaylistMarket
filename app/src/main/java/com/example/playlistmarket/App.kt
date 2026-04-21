package com.example.playlistmarket

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmarket.data.interfaceClient.ActivTrackClient
import com.example.playlistmarket.data.interfaceClient.NetworkClient
import com.example.playlistmarket.data.interfaceClient.ThemeClient
import com.example.playlistmarket.data.interfaceClient.TrackListClient
import com.example.playlistmarket.data.interfaceClient.TrackPositionClient
import com.example.playlistmarket.data.network.activTrack.ActivTrackRepositoryImpl
import com.example.playlistmarket.data.network.activTrack.StorageActivTrackClient
import com.example.playlistmarket.data.network.searchMusic.MusicRepositoryImpl
import com.example.playlistmarket.data.network.searchMusic.RetrofitNetworkClient
import com.example.playlistmarket.data.network.theme.StorageThemeClient
import com.example.playlistmarket.data.network.theme.ThemeRepositoryImpl
import com.example.playlistmarket.data.network.trackList.StorageListTrackClient
import com.example.playlistmarket.data.network.trackList.TrackListRepositoryImpl
import com.example.playlistmarket.data.network.trackPosition.SharedPrefsTrackPositionClient
import com.example.playlistmarket.data.network.trackPosition.TrackPositionRepositoryImpl
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackRepository
import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicRepository
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeRepository
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListRepository
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionRepository
import com.example.playlistmarket.domain.impl.ThemeInteractorImpl
import com.example.playlistmarket.domain.impl.TrackPositionInteractImpl
import com.example.playlistmarket.domain.lmpl.ActivTrackInteractorImpl
import com.example.playlistmarket.domain.lmpl.MusicInteractImpl
import com.example.playlistmarket.domain.lmpl.TrackListInteractorImpl
import com.example.playlistmarket.ui.MainActivity
import com.example.playlistmarket.ui.activity.AudioPlayer
import com.example.playlistmarket.ui.activity.SearchActivity
import com.example.playlistmarket.ui.activity.SettingsActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(modules = [AppModule::class ,AppClientModule ::class, AppRepositoryModule::class, AppInteractorModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(audioPlayer: AudioPlayer)
    fun inject(searchActivity: SearchActivity)
    fun inject(settingsActivity: SettingsActivity)
}

class App : Application(){
    val appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
    fun inject(mainActivity: MainActivity) {
       appComponent.inject(mainActivity)
    }
    fun inject(audioPlayer: AudioPlayer) {
        appComponent.inject(audioPlayer)
    }
    fun inject(searchActivity: SearchActivity) {
        appComponent.inject(searchActivity)
    }
    fun inject(settingsActivity: SettingsActivity) {
        appComponent.inject(settingsActivity)
    }
}



@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideContext(): Context = app.applicationContext

    @Provides
    fun provideMediaPlayer(): MediaPlayer  = MediaPlayer()
}

@Module
abstract class AppClientModule {

    @Binds
    abstract fun bindActivTrackClient(
        implementation: StorageActivTrackClient
    ): ActivTrackClient

    @Binds
    abstract fun bindNetworkClient(
        implementation: RetrofitNetworkClient
    ): NetworkClient

    @Binds
    abstract fun bindThemeClient(
        implementation: StorageThemeClient
    ): ThemeClient

    @Binds
    abstract fun bindTrackListClient(
        implementation: StorageListTrackClient
    ): TrackListClient

    @Binds
    abstract fun bindTrackPositionClient(
        implementation: SharedPrefsTrackPositionClient
    ): TrackPositionClient
}

@Module
abstract class AppRepositoryModule {

    @Binds
    abstract fun bindActivTrackRepository(
        implementation: ActivTrackRepositoryImpl
    ): ActivTrackRepository

    @Binds
    abstract fun bindMusicRepository(
        implementation: MusicRepositoryImpl
    ): MusicRepository

    @Binds
    abstract fun bindThemeRepository(
        implementation: ThemeRepositoryImpl
    ): ThemeRepository

    @Binds
    abstract fun bindTrackListRepository(
        implementation: TrackListRepositoryImpl
    ): TrackListRepository

    @Binds
    abstract fun bindTrackPositionRepository(
        implementation: TrackPositionRepositoryImpl
    ): TrackPositionRepository
}

@Module
abstract class AppInteractorModule {

    @Binds
    abstract fun bindActivTrackInteractor(
        implementation: ActivTrackInteractorImpl
    ): ActivTrackInteractor

    @Binds
    abstract fun bindMusicInteractor(
        implementation: MusicInteractImpl
    ): MusicInteractor

    @Binds
    abstract fun bindThemeInteractor(
        implementation: ThemeInteractorImpl
    ): ThemeInteractor

    @Binds
    abstract fun bindTrackListInteractor(
        implementation: TrackListInteractorImpl
    ): TrackListInteractor

    @Binds
    abstract fun bindTrackPositionInteractor(
        implementation: TrackPositionInteractImpl
    ): TrackPositionInteractor
}

