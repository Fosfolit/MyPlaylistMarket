package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.Constants
import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.api.activTrack.ActivTrackInteractor
import com.example.playlistmarket.domain.api.searchMisuc.MusicInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import java.util.LinkedList

class SearchViewModel(
    private var activTrack: ActivTrackInteractor,
    private var trackListInteractor: TrackListInteractor,
    private var musicInteractor: MusicInteractor
) : ViewModel() {
    open class Factory(
        private var musicInteractor: MusicInteractor,
        private var activTrack: ActivTrackInteractor,
        private var trackListInteractor: TrackListInteractor
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(
                activTrack = activTrack,
                trackListInteractor = trackListInteractor,
                musicInteractor = musicInteractor
            ) as T
        }
    }
    private val сondition = SearchState()
    private val viewCondition = MutableLiveData<SearchState>()
    val observeViewCondition: LiveData<SearchState> = viewCondition
    init {
        viewCondition.postValue(SearchState())
        loadHistoryListTrack()
    }


    fun vlil(it: DataMusic){
        musicInteractor.clickDebounce(object :
            MusicInteractor.BoolMusicConsumer {
            override fun consume(click: Boolean) {
                clickSearchObject(it)
                сondition.musicClick = click
                viewCondition.postValue(сondition)
            }
        })
    }

    fun loadHistoryListTrack(){
        trackListInteractor.loadListTrack(object : TrackListInteractor.LoadTrackList {
            override fun consume(list: LinkedList<DataMusic>) {
                сondition.listHistoryResult = list
                viewCondition.postValue(сondition)
            }
        })
    }

    fun swichHistoryListTrack(){
        сondition.pr = Constants.sostoinWie.HISTORY
        viewCondition.postValue(сondition)
    }

    fun clearHistoryTrack(){
        сondition.pr = Constants.sostoinWie.START
        viewCondition.postValue(сondition)
        trackListInteractor.saveListTrack(LinkedList<DataMusic>())
    }


    fun musicSearch(string: String) {
        сondition.pr = Constants.sostoinWie.LOAD
        viewCondition.postValue(сondition)
        try {
            musicInteractor.searchMusic(string, object : MusicInteractor.MusicConsumer {
                override fun consume(foundMusic: List<DataMusic>) {
                    if (foundMusic.isNotEmpty()) {
                        сondition.pr = Constants.sostoinWie.RESULT
                        сondition.listSearchResult = foundMusic
                        viewCondition.postValue(сondition)
                    } else {
                        сondition.pr = Constants.sostoinWie.ERR_FIND
                        viewCondition.postValue(сondition)
                    }
                }
            })
        } catch (e: Exception){
            сondition.pr = Constants.sostoinWie.ERR_INET
            viewCondition.postValue(сondition)
        }
    }


    fun clickSearchObject(it: DataMusic){
        trackListInteractor.addItem(it)
        activTrack.saveTrack(it)
        сondition.musicClick =true
        viewCondition.postValue(сondition)
    }



}
data class SearchState (
    var musicClick: Boolean = false,
    var listHistoryResult :List<DataMusic> = LinkedList<DataMusic>(),
    var listSearchResult :List<DataMusic> = LinkedList<DataMusic>(),
    var pr :Constants.sostoinWie = Constants.sostoinWie.START
)


