package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmarket.domain.api.interactor.ThemeInteractor
import kotlinx.coroutines.launch

class MainViewModel (
    private val themeInteraction : ThemeInteractor
) : ViewModel(){

    private val theme = MutableLiveData<Int>()
    val themeMode: LiveData<Int> = theme

    init{
        loadTheme()
    }

    private fun loadTheme(){
        viewModelScope.launch {
            updateTheme(themeInteraction.loadTheme())
        }
    }

    companion object {
        private const val LIGHT_THEME = 1
        private const val DARK_THEME = 2
    }

    fun updateTheme(isDarkMode: Boolean){
        if (isDarkMode){
            theme.postValue(DARK_THEME)
        } else{
            theme.postValue(LIGHT_THEME)
        }
    }

}