package com.example.playlistmarket.ui.viewModel

import android.widget.Adapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.TrackList
import com.example.playlistmarket.domain.api.interactor.ActivTrackInteractor
import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.model.SearchViewModelState
import java.util.LinkedList


class SearchViewModel (
    private val activeTrack: ActivTrackInteractor,
    private val trackListInteraction: TrackListInteractor,
    private val musicInteraction: MusicInteractor
) : ViewModel() {


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
            override fun consume(list: TrackList) {
                updateListHistory(list)
            }
        })
        updateClickStatus(false)
    }

    fun switchToHistory(){
        updateModelStatus(SearchViewModelState.HISTORY)
        updateClickStatus(false)
    }

    fun clearSearchHistory(){
        updateModelStatus(SearchViewModelState.START)
        trackListInteraction.clearListTrack()
        updateClickStatus(false)
    }

    fun searchMusic(query: String) {
        updateModelStatus(SearchViewModelState.LOAD)
        musicInteraction.searchMusic(query, object : MusicInteractor.MusicConsumer {
            override fun consume(foundMusicList: Result<TrackList>) {
                foundMusicList.onSuccess {
                    if (it.list.isNotEmpty()) {
                        updateListSearch(it)
                        updateModelStatus(SearchViewModelState.RESULT) }
                    else {
                        updateModelStatus(SearchViewModelState.ERR_FIND) }
                }
                foundMusicList.onFailure {

                    updateModelStatus(SearchViewModelState.ERR_INET)

                }
            }
        })
        updateClickStatus(false)
    }

    fun clickOnTrack(clickedTrack: DataMusic){
        trackListInteraction.addItem(clickedTrack)
        activeTrack.saveTrack(clickedTrack)
        updateClickStatus(true)
    }

    private fun updateModelStatus (status :SearchViewModelState){
        searchViewState.modelStatus = status
        searchState.postValue(searchViewState)
    }
    private fun updateClickStatus (status :Boolean){
        searchViewState.clickStatus = status
        searchState.postValue(searchViewState)
    }
    private fun updateListHistory (status : TrackList){
        searchViewState.listHistory = status.list
        searchState.postValue(searchViewState)
    }
    private fun updateListSearch (status : TrackList){
        searchViewState.listSearch = status.list
        searchState.postValue(searchViewState)
    }
    private fun updateErrorName (status :Int){
        searchViewState.errorName = status
        searchState.postValue(searchViewState)
    }

}
data class SearchState (
    var clickStatus: Boolean = false,
    var listHistory :List<DataMusic> = LinkedList<DataMusic>(),
    var listSearch :List<DataMusic> = LinkedList<DataMusic>(),
    var modelStatus :SearchViewModelState = SearchViewModelState.START,
    var errorName : Int = R.string.notInternetError1
)



