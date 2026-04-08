package com.example.playlistmarket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmarket.domain.api.theme.ThemeInteractor

class MainViewModel(
    private var themeInteractor : ThemeInteractor
) : ViewModel(){
    private val themee = MutableLiveData<Int>()
    val observeTheme: LiveData<Int> = themee


    open class Factory(
        private var themeInteractor : ThemeInteractor
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                themeInteractor = themeInteractor
            ) as T
        }
    }



    fun setTheme(){
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

}