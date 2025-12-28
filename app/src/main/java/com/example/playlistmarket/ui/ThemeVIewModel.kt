package com.example.playlistmarket.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmarket.Creator.provideThemeInteractor
import com.example.playlistmarket.domain.api.theme.ThemeInteractor
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer

class ThemeVIewModel(private val context: Context) : ViewModel(){
    private lateinit var d : ThemeInteractor
init {
    d = provideThemeInteractor(context)
}

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getFactory(value: Int): ViewModelProvider.Factory = viewModelFactory {

        }
    }



    private val counterLiveData = MutableLiveData<Boolean>(


    )
    fun observeCounter(): LiveData<Boolean> = counterLiveData
    private var darktheme = false

    fun getCounter(): Boolean {
        return darktheme
    }

    fun incrementCounter(theme:Boolean) {
        darktheme = theme
    }
}