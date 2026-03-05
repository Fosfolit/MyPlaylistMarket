package com.example.playlistmarket.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeInteractor

class SettingsViewModel  : ViewModel(){
    private lateinit var themeInteractor : ThemeInteractor
    private val themee = MutableLiveData<Int>()
    val observeTheme: LiveData<Int> = themee
    fun setContext(context: Context){
        themeInteractor = provideThemeInteractor(context)
        themeInteractor.loadTheme(object : ThemeInteractor.ThemeConsumer {
            override fun consume(theme: Boolean) {
                if (theme) {
                    themee.postValue(2)
                } else {
                    themee.postValue(1)
                }
            }
        }
        )
    }
    fun settheme(theme: Boolean){
        themeInteractor.saveTheme(theme)
        if (theme){
            themee.postValue(2)
        } else{
            themee.postValue(1)
        }

    }

}