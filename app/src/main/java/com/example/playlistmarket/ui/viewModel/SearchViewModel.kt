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
    private val activeTrack: ActivTrackInteractor,
    private val trackListInteraction: TrackListInteractor,
    private val musicInteraction: MusicInteractor
) : ViewModel() {
    open class Factory(
        private val musicInteraction: MusicInteractor,
        private val activeTrack: ActivTrackInteractor,
        private val trackListInteraction: TrackListInteractor
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(
                activeTrack = activeTrack,
                trackListInteraction = trackListInteraction,
                musicInteraction = musicInteraction
            ) as T
        }
    }

    private val searchViewState = SearchState()
    private val searchState = MutableLiveData<SearchState>()
    val observeSearchState: LiveData<SearchState> = searchState

    init {
        searchState.postValue(SearchState())
        loadSearchHistory()
    }

    fun handleTrackClick(track: DataMusic){
        musicInteraction.clickDebounce(object :
            MusicInteractor.BoolMusicConsumer {
            override fun consume(click: Boolean) {
                clickOnTrack(track)
                updateClickStatus(click)
            }
        })
    }

    private fun loadSearchHistory(){
        trackListInteraction.loadListTrack(object : TrackListInteractor.LoadTrackList {
            override fun consume(list: LinkedList<DataMusic>) {
                updateListHistory(list)
            }
        })
        updateClickStatus(false)
    }

    fun switchToHistory(){
        updateModelStatus(Constants.sostoinWie.HISTORY)
        updateClickStatus(false)
    }

    fun clearSearchHistory(){
        updateModelStatus(Constants.sostoinWie.START)
        trackListInteraction.clearListTrack()
        updateClickStatus(false)
    }

    fun searchMusic(query: String) {
        updateModelStatus(Constants.sostoinWie.LOAD)
        try {
            musicInteraction.searchMusic(query, object : MusicInteractor.MusicConsumer {
                override fun consume(foundMusicList: List<DataMusic>) {
                    if (foundMusicList.isNotEmpty()) {
                        updateListSearch(foundMusicList)
                        updateModelStatus(Constants.sostoinWie.RESULT)
                    } else {
                        updateModelStatus(Constants.sostoinWie.ERR_FIND)
                    }
                }

            })
        } catch (e: Exception){
            updateModelStatus(Constants.sostoinWie.ERR_INET)
        }
        updateClickStatus(false)
    }

    fun clickOnTrack(clickedTrack: DataMusic){
        trackListInteraction.addItem(clickedTrack)
        activeTrack.saveTrack(clickedTrack)
        updateClickStatus(true)
    }

    private fun updateModelStatus (status :Constants.sostoinWie){
        searchViewState.modelStatus = status
        searchState.postValue(searchViewState)
    }
    private fun updateClickStatus (status :Boolean){
        searchViewState.clickStatus = status
        searchState.postValue(searchViewState)
    }
    private fun updateListHistory (status :List<DataMusic>){
        searchViewState.listHistory = status
        searchState.postValue(searchViewState)
    }
    private fun updateListSearch (status :List<DataMusic>){
        searchViewState.listSearch = status
        searchState.postValue(searchViewState)
    }

}
data class SearchState (
    var clickStatus: Boolean = false,
    var listHistory :List<DataMusic> = LinkedList<DataMusic>(),
    var listSearch :List<DataMusic> = LinkedList<DataMusic>(),
    var modelStatus :Constants.sostoinWie = Constants.sostoinWie.START
)



