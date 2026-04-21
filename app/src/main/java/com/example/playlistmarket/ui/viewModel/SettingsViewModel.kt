package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(
    private val themeInteractor : ThemeInteractor
) : ViewModel(){

    open class Factory @Inject constructor(
        private val themeInteractor : ThemeInteractor
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                themeInteractor = themeInteractor
            ) as T
        }
    }


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
        themeInteractor.loadTheme(object : ThemeInteractor.ThemeConsumer {
            override fun consume(isDarkMode: Boolean) {
                updateTheme(isDarkMode)
            }
        })
    }

    fun saveUpdateTheme(isDarkMode: Boolean){
        themeInteractor.saveTheme(isDarkMode)
        updateTheme(isDarkMode)
    }

    fun updateTheme(isDarkMode: Boolean){
        if (isDarkMode){
            theme.postValue(DARK_THEME)
        } else{
            theme.postValue(LIGHT_THEME)
        }
    }

}
