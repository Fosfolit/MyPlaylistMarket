package com.example.playlistmarket.ui.viewModel.MediaLibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.model.DataMusic
import com.example.playlistmarket.domain.model.MLPlaylistState
import com.example.playlistmarket.domain.model.TrackList
import java.util.LinkedList


class MLFavoriteTracksViewModel(
    private val trackListInteraction: TrackListInteractor
): ViewModel() {
    private val condition = MLPViewModelStates()
    private val mediaLibraryPlaylistVMState = MutableLiveData<MLPViewModelStates>()
    val observeMediaLibraryPlaylistVMState: LiveData<MLPViewModelStates> = mediaLibraryPlaylistVMState

    init {
        mediaLibraryPlaylistVMState.postValue(MLPViewModelStates())
        loadTrack()
    }

    fun loadTrack(){
        trackListInteraction.loadListTrack(object : TrackListInteractor.LoadTrackList {
            override fun consume(list: TrackList) {
                updateListPlaylist(list.list)
            }
        })
    }





    private fun updateMediaLibraryPlaylist (status :MLPlaylistState){
        condition.mediaLibraryPlaylistState = status
        mediaLibraryPlaylistVMState.postValue(condition)
    }
    private fun updateListPlaylist (status : List<DataMusic>){
        condition.listPlaylist = status
        mediaLibraryPlaylistVMState.postValue(condition)
    }
}


data class MLFavoriteTrackVMStates (
    var mediaLibraryPlaylistState : MLPlaylistState = MLPlaylistState.STATE_ERROR,
    var listPlaylist :List<DataMusic> = LinkedList<DataMusic>(),
)
