package com.example.playlistmarket.ui.viewModel

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.Constants
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor

class AudioPlayerViewModel(
    private val trackPositionInteraction: TrackPositionInteractor,
    private val activeTrack : ActivTrackInteractor,
    private val mediaPlayer : MediaPlayer
): ViewModel() {

    open class Factory(
        private val trackPositionInteraction: TrackPositionInteractor,
        private val activeTrack: ActivTrackInteractor,
        private val mediaPlayer : MediaPlayer
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AudioPlayerViewModel(
                trackPositionInteraction = trackPositionInteraction,
                activeTrack = activeTrack,
                mediaPlayer = mediaPlayer
            ) as T
        }
    }

    private val condition = PlayerUiState()
    private val playerUiState = MutableLiveData<PlayerUiState>()
    val observePlayerUiState: LiveData<PlayerUiState> = playerUiState
    private lateinit var currentTrackUrl : String
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timerUpdateRunnable: Runnable


    init {
        playerUiState.postValue(PlayerUiState())
        updatePlayerState(Constants.PlayerState.STATE_DEFAULT)
        loadTrack()
        timerUpdateRunnable = Runnable {
            updateCurrentPosition(mediaPlayer.currentPosition)
            handler.postDelayed( timerUpdateRunnable, 1000)
        }
    }



    private fun loadTrack(){
        activeTrack.loadTrack(object : ActivTrackInteractor. ActivTrackConsumer {
            override fun consume(savedTrack: DataMusic) {
                updateCurrentTrack(savedTrack)
                currentTrackUrl = savedTrack.previewUrl
                trackPositionInteraction.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
                    override fun consume(savedPosition: TrackPosition) {
                        updateTrackPosition(savedPosition)
                        prepareMedia(savedPosition, savedTrack)
                    }
                })
            }
        })
    }





    private fun prepareMedia(savedPosition :TrackPosition, savedTrack: DataMusic){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(savedTrack.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            updatePlayerState(Constants.PlayerState.STATE_PREPARED)
            if (savedPosition.trackUrl != savedTrack.previewUrl){
                mediaPlayer.seekTo(0)
            } else{
                mediaPlayer.seekTo(savedPosition.position)
            }
            updateCurrentPosition(mediaPlayer.currentPosition)
        }
        mediaPlayer.setOnCompletionListener {
            updatePlayerState(Constants.PlayerState.STATE_PREPARED)
            mediaPlayer.seekTo(0)
        }
    }



    fun saveTrackPosition(){
        stopPlayback()
        if (currentTrackUrl.isNotEmpty()){
            trackPositionInteraction.saveTrackPosition(TrackPosition(currentTrackUrl,mediaPlayer.currentPosition))
        }
    }

    fun togglePlayback (){
        when(condition.playerState) {
            Constants.PlayerState.STATE_PLAYING -> {
                updatePlayerState(Constants.PlayerState.STATE_PAUSED)
                handler.removeCallbacks(timerUpdateRunnable)
                mediaPlayer.pause()
            }
            Constants.PlayerState.STATE_PREPARED, Constants.PlayerState.STATE_PAUSED -> {
                updatePlayerState(Constants.PlayerState.STATE_PLAYING)
                mediaPlayer.start()
                handler.post(timerUpdateRunnable)
            }
            else ->{
                //  Toast.makeText(context, playerState.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun stopPlayback(){
        handler.removeCallbacks(timerUpdateRunnable)
        mediaPlayer.stop()
    }

    private fun updateCurrentPosition(positionMs: Int){
        condition.currentPosition = positionMs
        playerUiState.postValue(condition)
    }
    private fun updatePlayerState(state: Constants.PlayerState){
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

data class PlayerUiState (
    var currentPosition: Int = 0,
    var playerState :Constants.PlayerState = Constants.PlayerState.STATE_DEFAULT,
    var thisTrack: DataMusic  = DataMusic("","","",0,"","","","",""),
    var trackPosition: TrackPosition = TrackPosition("",0)
)



