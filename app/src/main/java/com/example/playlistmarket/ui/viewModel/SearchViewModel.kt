package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmarket.R
import com.example.playlistmarket.domain.api.interactor.ActivTrackInteractor
import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.SearchViewModelState
import com.example.playlistmarket.domain.model.TrackList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.LinkedList


class SearchViewModel (
    private val activeTrack: ActivTrackInteractor,
    private val trackListHistoryInteraction: TrackListInteractor,
    private val musicInteraction: MusicInteractor
) : ViewModel() {

    private var searchJob: Job? = null
    private val searchViewState = SearchState()
    private val searchState = MutableLiveData<SearchState>()
    val observeSearchState: LiveData<SearchState> = searchState
    private var lastQuery: String = ""

    init {
        searchState.postValue(SearchState())
        loadSearchHistory()
        updateClickStatus(false)
    }

    fun clickOnTrack(clickedTrack: DataMusic){
        viewModelScope.launch {
            trackListHistoryInteraction.addItem(clickedTrack)
            activeTrack.saveTrack(clickedTrack)
            updateClickStatus(true)
        }
    }

    private fun loadSearchHistory(){
        viewModelScope.launch {
            updateListHistory(trackListHistoryInteraction.loadListTrack())
            updateClickStatus(false)
        }
    }

    fun clearSearchHistory(){
        updateModelStatus(SearchViewModelState.START)
        viewModelScope.launch {
            trackListHistoryInteraction.clearListTrack()
        }
    }

    fun switchToHistory(){
        searchJob?.cancel()
        loadSearchHistory()
        updateModelStatus(SearchViewModelState.HISTORY)
    }

    fun searchMusic(query: String) {
        lastQuery = query
        updateModelStatus(SearchViewModelState.LOAD)
        searchJob = viewModelScope.launch {
            musicInteraction
                .searchMusic(query)
                .collect {
                    if (query == lastQuery){
                    if (searchViewState.modelStatus == SearchViewModelState.LOAD){
                    if (it != null) {
                        it.onSuccess {
                            if (it.list.isNotEmpty()) {
                                updateListSearch(it)
                                updateModelStatus(SearchViewModelState.RESULT) } else {
                                updateModelStatus(SearchViewModelState.ERR_FIND) }
                        }

                    it.onFailure {
                        /*
                        when (it){
                            is NullPointerException ->{updateErrorName(R.string.nullPointerException)}
                            is SSLHandshakeException ->{updateErrorName(R.string.sSLHandshakeException)}
                            is SocketTimeoutException ->{updateErrorName(R.string.socketTimeoutException)}
                            is UnknownHostException ->{updateErrorName(R.string.unknownHostException)}
                            is ClassCastException ->{updateErrorName(R.string.classCastException)}
                            is ConnectException ->{updateErrorName(R.string.сonnectException)}
                        }*/
                        if (searchViewState.modelStatus == SearchViewModelState.LOAD) {
                            updateModelStatus(SearchViewModelState.ERR_INET)
                        }
                    }
                    }
                }}}

        }
    }


    fun deleteClickStatus (){
        searchViewState.clickStatus = false
        searchState.postValue(searchViewState)
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
    var errorName : Int = R.string.app_name
)



