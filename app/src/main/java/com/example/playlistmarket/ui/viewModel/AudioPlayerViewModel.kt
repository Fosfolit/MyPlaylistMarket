package com.example.playlistmarket.ui.viewModel

import android.content.Context
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
    private val trackPositionInteractor: TrackPositionInteractor,
    private var activTrack : ActivTrackInteractor,
    private var mediaPlayer : MediaPlayer
): ViewModel() {

    open class Factory(
        private val trackPositionInteractor: TrackPositionInteractor,
        private val activTrack: ActivTrackInteractor,
        private var mediaPlayer : MediaPlayer
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AudioPlayerViewModel(
                trackPositionInteractor = trackPositionInteractor,
                activTrack = activTrack,
                mediaPlayer = mediaPlayer
            ) as T
        }
    }
    private val сondition = PlayerState()
    private val viewCondition = MutableLiveData<PlayerState>()
    val observeViewCondition: LiveData<PlayerState> = viewCondition
    init {
        viewCondition.postValue(PlayerState())
    }




    private lateinit var url : String

    private fun loadTrack(){
        activTrack.loadTrack(object : ActivTrackInteractor. ActivTrackConsumer {
            override fun consume(expression: DataMusic) {
                сondition.thisTrack = expression
                viewCondition.postValue(сondition)
                url = expression.previewUrl
                trackPositionInteractor.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
                    override fun consume(track: TrackPosition) {
                        сondition.trackPosition = track
                        viewCondition.postValue(сondition)
                            // Toast.makeText(context, сondition.trackPosition.trackUrl, Toast.LENGTH_SHORT).show()
                        prepareMedia(track, expression)
                    }
                })
            }
        })
    }





    private fun prepareMedia(nowTrackPosition :TrackPosition,nowThisTrack: DataMusic){
        mediaPlayer.reset()
        mediaPlayer.setDataSource(nowThisTrack.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            сondition.playerState = Constants.PlayerState.STATE_PREPARED
            viewCondition.postValue(сondition)
            if (nowTrackPosition.trackUrl != nowThisTrack.previewUrl){
                mediaPlayer.seekTo(0)
            } else{
                mediaPlayer.seekTo(nowTrackPosition.position)
            }
            сondition.timerText = mediaPlayer.currentPosition
            viewCondition.postValue(сondition)
        }
        mediaPlayer.setOnCompletionListener {
            сondition.playerState = Constants.PlayerState.STATE_PREPARED
            viewCondition.postValue(сondition)
            mediaPlayer.seekTo(0)
        }
    }




    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    fun viewActiv(){
        сondition.playerState = Constants.PlayerState.STATE_DEFAULT
        viewCondition.postValue(сondition)
        loadTrack()
        runnable = Runnable {
            сondition.timerText = mediaPlayer.currentPosition
            viewCondition.postValue(сondition)
            handler.postDelayed( runnable, 1000)
        }
    }


    fun saveTrac(){
        medioStop()
        if (url.isNotEmpty()){
            trackPositionInteractor.saveTrackPosition(TrackPosition(url,mediaPlayer.currentPosition))
        }
    }
    fun mediaPlayerSwitch (){
        when(сondition.playerState) {
            Constants.PlayerState.STATE_PLAYING -> {
                сondition.playerState = Constants.PlayerState.STATE_PAUSED
                viewCondition.postValue(сondition)
                handler.removeCallbacks(runnable)
                mediaPlayer.pause()
            }
            Constants.PlayerState.STATE_PREPARED, Constants.PlayerState.STATE_PAUSED -> {
                сondition.playerState = Constants.PlayerState.STATE_PLAYING
                viewCondition.postValue(сondition)
                mediaPlayer.start()
                handler.post(runnable)
            }
            else ->{
                //  Toast.makeText(context, playerState.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun medioStop(){
        handler.removeCallbacks(runnable)
        mediaPlayer.stop()
    }


}

data class PlayerState (
    var timerText: Int = 0,
    var playerState :Constants.PlayerState = Constants.PlayerState.STATE_DEFAULT,
    var thisTrack: DataMusic  = DataMusic("","","",0,"","","","",""),
    var trackPosition: TrackPosition = TrackPosition("",0)
)



