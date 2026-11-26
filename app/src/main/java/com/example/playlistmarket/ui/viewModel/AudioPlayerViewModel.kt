package com.example.playlistmarket.ui.viewModel

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmarket.domain.api.interactor.ActivTrackInteractor
import com.example.playlistmarket.domain.api.interactor.TrackPositionInteractor
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.PlayerState
import com.example.playlistmarket.domain.model.TrackPosition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.time.Timer

class AudioPlayerViewModel (
    private val trackPositionInteraction: TrackPositionInteractor,
    private val activeTrack : ActivTrackInteractor,
    private val mediaPlayer : MediaPlayer
): ViewModel() {

    private val condition = PlayerUiState()
    private val playerUiState = MutableLiveData<PlayerUiState>()
    val observePlayerUiState: LiveData<PlayerUiState> = playerUiState
    private lateinit var currentTrackUrl : String
    private var timerJob: Job? = null

    init {
        playerUiState.postValue(PlayerUiState())
        updatePlayerState(PlayerState.STATE_DEFAULT)
        loadTrack()
    }

    private fun timerStart(){
        timerJob = viewModelScope.launch {
            while(isActive){
                updateCurrentPosition(mediaPlayer.currentPosition)
                delay(300L)
            }
        }
    }

    private fun timerStop() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun loadTrack(){
        viewModelScope.launch {
            val savedTrack = activeTrack.loadTrack()
            activeTrack.saveTrack(DataMusic("", "", "", "00:00", "", "", "", "", ""))
            updateCurrentTrack(savedTrack)
            currentTrackUrl = savedTrack.previewUrl
            loadTrackPosition(savedTrack)
        }
    }

    private fun loadTrackPosition(savedTrack: DataMusic){
        viewModelScope.launch {
            val savedPosition = trackPositionInteraction.loadTrackPosition()
            updateTrackPosition(savedPosition)
            prepareMedia(savedPosition, savedTrack)
        }

    }






    private fun prepareMedia(savedPosition : TrackPosition, savedTrack: DataMusic){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(savedTrack.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            updatePlayerState(PlayerState.STATE_PREPARED)
            if (savedPosition.trackUrl != savedTrack.previewUrl){
                mediaPlayer.seekTo(0)
            } else{
                mediaPlayer.seekTo(savedPosition.position)
            }
            updateCurrentPosition(mediaPlayer.currentPosition)
        }
        mediaPlayer.setOnCompletionListener {
            updatePlayerState(PlayerState.STATE_PREPARED)
            updateCurrentPosition(0)
            mediaPlayer.seekTo(0)
        }
    }



    fun saveTrackPosition(){
        stopPlayback()
        if (currentTrackUrl.isNotEmpty()){
            viewModelScope.launch {
                trackPositionInteraction.saveTrackPosition(TrackPosition(currentTrackUrl,mediaPlayer.currentPosition))
            }
        }
    }

    fun togglePlayback (){
        when(condition.playerState) {
            PlayerState.STATE_PLAYING -> {
                updatePlayerState(PlayerState.STATE_PAUSED)
                timerStop()
                mediaPlayer.pause()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                updatePlayerState(PlayerState.STATE_PLAYING)
                mediaPlayer.start()
                timerStart()
            }
            else ->{
                //  Toast.makeText(context, playerState.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun stopPlayback(){
        timerStop()
        mediaPlayer.stop()
    }

    private fun updateCurrentPosition(positionMs: Int){
        condition.currentPosition = positionMs
        playerUiState.postValue(condition)
    }
    private fun updatePlayerState(state: PlayerState){
        condition.playerState = state
        playerUiState.postValue(condition)
    }
    private fun updateCurrentTrack(track: DataMusic){
        condition.thisTrack = track
        playerUiState.postValue(condition)
    }
    private fun updateTrackPosition(position: TrackPosition){
        condition.trackPosition = position
        playerUiState.postValue(condition)
    }
}

data class PlayerUiState(
    var currentPosition: Int = 0,
    var playerState: PlayerState = PlayerState.STATE_DEFAULT,
    var thisTrack: DataMusic = DataMusic("", "", "", "00:00", "", "", "", "", ""),
    var trackPosition: TrackPosition = TrackPosition("", 0)
)



