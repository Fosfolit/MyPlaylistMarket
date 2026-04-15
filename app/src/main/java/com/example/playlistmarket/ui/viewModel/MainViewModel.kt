package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.domain.api.theme.ThemeInteractor

class MainViewModel(
    private val themeInteraction : ThemeInteractor
) : ViewModel(){

    open class Factory(
        private val themeInteraction : ThemeInteractor
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                themeInteraction = themeInteraction
            ) as T
        }
    }

    private val theme = MutableLiveData<Int>()
    val themeMode: LiveData<Int> = theme

    init{
        loadTheme()
    }

    private fun loadTheme(){
        themeInteraction.loadTheme(object : ThemeInteractor.ThemeConsumer {
            override fun consume(isDarkMode: Boolean) {
                updateTheme(isDarkMode)
            }
        }
        )
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