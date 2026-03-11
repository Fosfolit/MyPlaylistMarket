package com.example.playlistmarket.ui.viewModel

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideStorageInteractor
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor

class AudioPlayerViewModel: ViewModel() {


    private lateinit var trackPositionInteractor: TrackPositionInteractor
    private val trackPosition = MutableLiveData<TrackPosition>()
    private fun loadTrackPosition(context: Context){
        trackPositionInteractor = provideStorageInteractor(context)
        trackPositionInteractor.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
            override fun consume(track: TrackPosition) {
                trackPosition.postValue(track)
            }
        })
      //  Toast.makeText(context, "Позиция трека загружена", Toast.LENGTH_SHORT).show()

}


    private lateinit var activTrack : ActivTrackInteractor
    private val thisTrack = MutableLiveData<DataMusic>()
    val observeThisTrack: LiveData<DataMusic> = thisTrack
    private lateinit var url : String
    private fun loadTrack(context: Context){
        activTrack = provideActivTrackInteractor(context)
        activTrack.loadTrack(object : ActivTrackInteractor. ActivTrackConsumer {
            override fun consume(expression: DataMusic) {
                thisTrack.postValue(expression)
                url = expression.previewUrl
            }
        })
     //   Toast.makeText(context, "трек загружен", Toast.LENGTH_SHORT).show()

}



    val result = MediatorLiveData<Pair<TrackPosition, DataMusic>>().apply {

        var source1Changed = false
        var source2Changed = false

        var latestValue1: TrackPosition? = null
        var latestValue2: DataMusic? = null

        addSource(trackPosition) { value1 ->
            latestValue1 = value1
            source1Changed = true

            if (source2Changed) {
                latestValue1?.let { v1 ->
                    latestValue2?.let { v2 ->

                        value = Pair(v1, v2)

                        source1Changed = false
                        source2Changed = false
                    }
                }
            }
        }

        addSource(thisTrack) { value2 ->
            latestValue2 = value2
            source2Changed = true

            if (source1Changed) {
                latestValue1?.let { v1 ->
                    latestValue2?.let { v2 ->

                        value = Pair(v1, v2)

                        source1Changed = false
                        source2Changed = false
                    }
                }
            }
        }
    }


    private var mediaPlayer = MediaPlayer()
    enum class PlayerState  {
        STATE_DEFAULT ,
        STATE_PREPARED ,
        STATE_PLAYING ,
        STATE_PAUSED
    }//Виды состояния медиа плеера
    private val playerState = MutableLiveData<PlayerState>()
    val observePlayerState: LiveData<PlayerState> = playerState
    private val timerText = MutableLiveData<Int>()
    val observeTimerText: LiveData<Int> = timerText
    private fun prepareMedia(nowTrackPosition :TrackPosition,nowThisTrack: DataMusic){
        mediaPlayer.setDataSource(nowThisTrack.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState.postValue(PlayerState.STATE_PREPARED)
            if (nowTrackPosition.trackUrl != nowThisTrack.previewUrl){
                mediaPlayer.seekTo(0)
            } else{
                mediaPlayer.seekTo(nowTrackPosition.position)
            }
            timerText.postValue(mediaPlayer.currentPosition)
        }

        mediaPlayer.setOnCompletionListener {
            playerState.postValue(PlayerState.STATE_PREPARED)
            mediaPlayer.seekTo(0)
        }



    }




    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    fun setContext(context: Context){
        playerState.postValue(PlayerState.STATE_DEFAULT)
        result.observeForever { par->
            prepareMedia(par.first,par.second)
          //  Toast.makeText(context, "медиосми"+ mediaPlayer.currentPosition, Toast.LENGTH_SHORT).show()
           // Toast.makeText(context, "медио вре"+ par.first.position, Toast.LENGTH_SHORT).show()
    }
       // timerText.observeForever {i ->
         //   Toast.makeText(context, "Время установлено"+ i, Toast.LENGTH_SHORT).show()}

        loadTrackPosition(context)
        loadTrack(context)
        runnable = Runnable {
            timerText.postValue(mediaPlayer.currentPosition)
            handler.postDelayed( runnable, 1000) // this = текущий Runnable
        }
    }


    fun saveTrac(context: Context){
        medioStop()
       // Toast.makeText(context, "ССылка"+ url, Toast.LENGTH_SHORT).show()
    if (url.isNotEmpty()){
            trackPositionInteractor.saveTrackPosition(TrackPosition(url,mediaPlayer.currentPosition))
      //  Toast.makeText(context, "Воемя"+ mediaPlayer.currentPosition, Toast.LENGTH_SHORT).show()
    }
    }
    fun mediaPlayerSwitch (){
            when(playerState.value) {
                PlayerState.STATE_PLAYING -> {
                    playerState.postValue(PlayerState.STATE_PAUSED)
                    handler.removeCallbacks(runnable)
                    mediaPlayer.pause()
                }
               PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                   playerState.postValue(PlayerState.STATE_PLAYING)
                   mediaPlayer.start()
                   handler.post(runnable)
                }
                else ->{
                }
            }
    }
    fun medioStop(){
        handler.removeCallbacks(runnable)
        mediaPlayer.stop()
    }





}