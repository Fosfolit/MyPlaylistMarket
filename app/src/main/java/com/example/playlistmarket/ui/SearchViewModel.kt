package com.example.playlistmarket.ui

import androidx.lifecycle.ViewModel

class SearchViewModel() : ViewModel(){
    private var counterValue = 0

    fun getCounter(): String {
        return counterValue.toString()
    }

    fun incrementCounter() {
        counterValue++
    }
}