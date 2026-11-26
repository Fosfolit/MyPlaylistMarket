package com.example.playlistmarket.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmarket.domain.api.interactor.ThemeInteractor
import kotlinx.coroutines.launch

class SettingsViewModel  (
    private val themeInteractor : ThemeInteractor
) : ViewModel(){



    private val theme = MutableLiveData<Int>()
    val themeMode: LiveData<Int> = theme

    init{
        loadTheme()
    }

    companion object {
        private const val LIGHT_THEME = 1
        private const val DARK_THEME = 2
    }

    private fun loadTheme(){
        viewModelScope.launch {
            updateTheme(themeInteractor.loadTheme())
        }
    }


    fun saveUpdateTheme(isDarkMode: Boolean){
        viewModelScope.launch {
            themeInteractor.saveTheme(isDarkMode)
            updateTheme(themeInteractor.loadTheme())
        }
    }

    fun updateTheme(isDarkMode: Boolean){
        if (isDarkMode){
            theme.postValue(DARK_THEME)
        } else{
            theme.postValue(LIGHT_THEME)
        }
    }

}
