package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.interfaceClient.ThemeClient


class StorageThemeClient (
    private val sharedPrefs: SharedPreferences
) : ThemeClient {

    override fun loadTheme(): Boolean {
        if(sharedPrefs.contains("Theme")) {
            return  sharedPrefs.getBoolean("Theme",false)
        }
        return false
    }

    override fun saveTheme(theme: Boolean) {
        sharedPrefs.edit()
            .remove("Theme")
            .putBoolean("Theme",theme)
            .apply()
    }
}
