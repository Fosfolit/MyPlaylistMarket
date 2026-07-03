package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MediaLibraryViewModel : ViewModel() {
    private val mediaLibViewState = MediaLibState()
    private val mediaLibraryState = MutableLiveData<MediaLibState>()
    val observeMediaLibraryState: LiveData<MediaLibState> = mediaLibraryState


    fun switchTabs(count: Int){
        updateTabsStatus(count)
    }


    private fun updateTabsStatus(count: Int){
        mediaLibViewState.tabsStatus = count
        mediaLibraryState.postValue(mediaLibViewState)
    }
}
data class MediaLibState (
    var tabsStatus: Int = 1
)