package com.example.playlistmarket.data.network.theme

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmarket.Constants.PRACTICUM_EXAMPLE_PREFERENCES
import com.example.playlistmarket.data.interfaceClient.ThemeClient

class StorageThemeClient(context: Context):ThemeClient {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        PRACTICUM_EXAMPLE_PREFERENCES,
        Context.MODE_PRIVATE
    )
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