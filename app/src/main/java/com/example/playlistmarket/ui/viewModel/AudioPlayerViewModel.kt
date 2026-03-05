package com.example.playlistmarket.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideAudioInteractor
import com.example.playlistmarket.Creator.provideStorageInteractor
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackPosition
import com.example.playlistmarket.domain.api.AudioInteractor
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.trackPosition.TrackPositionInteractor
import com.example.playlistmarket.presentation.MediaPlayerMy

class AudioPlayerViewModel: ViewModel() {
    fun setContext(context: Context){
        trackPositionInteractor = provideStorageInteractor(context)
        activTrack = provideActivTrackInteractor(context)
    }
    private var plaer : AudioInteractor = provideAudioInteractor()
    fun plaerStop (){
        plaer.stopPlay()
    }
    fun plaerAudioSwitch (){
        plaer.AudioSwitch()
    }
    private val thisTrack = MutableLiveData<DataMusic>()
    val observeThisTrack: LiveData<DataMusic> = thisTrack
    private lateinit var activTrack : ActivTrackInteractor
    fun trackLoad(){
        activTrack.loadTrack(object : ActivTrackInteractor. ActivTrackConsumer {
            override fun consume(expression: DataMusic) {
                thisTrack.postValue(expression)
                trackPositionInteractor.loadTrackPosition(object : TrackPositionInteractor.StorageConsumer {
                    override fun consume(track: TrackPosition) {
                        trackPosition.postValue(track)
                        if (expression.previewUrl !=track.trackUrl){
                            trackPosition.postValue(TrackPosition(expression.previewUrl , 0))
                        }
                    }
                })
            }
        })
    }
    private lateinit var trackPositionInteractor: TrackPositionInteractor
    private val trackPosition = MutableLiveData<TrackPosition>()
    val observeTrackPosition: LiveData<TrackPosition> = trackPosition

    fun plaerPrepare (playerMy: MediaPlayerMy){
        plaer.prepare(playerMy)
    }
    fun saveTrac(){
        var Ulrd : String = thisTrack.value!!.previewUrl
        trackPositionInteractor.saveTrackPosition(TrackPosition(Ulrd,plaer.getTime()))
    }

}