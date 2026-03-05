package com.example.playlistmarket.ui.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.example.playlistmarket.Creator.provideActivTrackInteractor
import com.example.playlistmarket.Creator.provideMusicInteractor
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.Creator.provideTrackListInteractor
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.ButtonVisibility
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.ErrorAdapter
import com.example.playlistmarket.domain.ErrorData
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.searchMusic.MusicInteractor
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.ui.MusicAdapter
import com.example.playlistmarket.ui.SearchedQueriesButtonAdapter
import com.example.playlistmarket.ui.SearchedQueriesTextAdapter
import java.util.LinkedList

class SearchViewModel() : ViewModel(){
    private lateinit var activTrack : ActivTrackInteractor
    private val progressBar = MutableLiveData<Boolean>()
    var observeProgressBar: LiveData<Boolean> = progressBar
    fun setContext(context: Context){
        activTrack = provideActivTrackInteractor(context)
        trackListInteractor = provideTrackListInteractor(context)
    }
    fun activSave(dataForSave : DataMusic){
        activTrack.saveTrack(dataForSave)
    }

    private  var musicInteractor : MusicInteractor = provideMusicInteractor()
    private val music = MutableLiveData<List<DataMusic>>()
    var observeMusic: LiveData<List<DataMusic>> = music
    fun musicSearch(string: String){
        progressBar.postValue(true)
        musicInteractor.searchMusic(string, object : MusicInteractor.MusicConsumer {
            override fun consume(foundMusic: List<DataMusic>) {
                    progressBar.postValue(true)
                    music.postValue(foundMusic)
            }
        })
    }
    private val musicClick = MutableLiveData<Boolean>()
    var observeMusicClick: LiveData<Boolean> = musicClick
    fun musicClick(){
        musicInteractor.clickDebounce(object :MusicInteractor.BoolMusicConsumer{
            override fun consume(click: Boolean) {
                musicClick.postValue(click)
            }
        })
    }

    private lateinit var trackListInteractor : TrackListInteractor
    fun trackListAdd (dataMusic: DataMusic){
        trackListInteractor.addItem(dataMusic)
    }
    fun trackListSave (dataMusic: LinkedList<DataMusic>){
        trackListInteractor.saveListTrack(dataMusic)
    }
    private val trackLoadList = MutableLiveData<LinkedList<DataMusic>>()
    var observetrackLoadList: LiveData<LinkedList<DataMusic>> = trackLoadList
    fun loadListTrack (){
        trackListInteractor.loadListTrack(object : TrackListInteractor.LoadTrackList{
            override fun consume(list: LinkedList<DataMusic>) {
                trackLoadList.postValue(list)
            }
        } )}
    private val trackIsEmpty = MutableLiveData<Boolean>()
    var observeTrackIsEmpty: LiveData<Boolean> = trackIsEmpty
    fun trackListIsEmpty (){
        progressBar.postValue(false)
        trackListInteractor.isEmpty(object : TrackListInteractor.EmptyTrackList {
            override fun consume(list: Boolean) {
                trackIsEmpty.postValue(list)
            }
        })

    }

}

