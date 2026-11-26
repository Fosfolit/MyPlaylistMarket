package com.example.playlistmarket.data.network.client

import android.content.SharedPreferences
import com.example.playlistmarket.data.interfaceClient.ThemeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext


class StorageThemeClient (
    private val sharedPrefs: SharedPreferences
) : ThemeClient {

    override suspend fun loadTheme(): Boolean =
        withContext(Dispatchers.IO) {
            if (sharedPrefs.contains("Theme")) {
                sharedPrefs.getBoolean("Theme", false)
            } else {
                false
            }
        }


    override suspend fun saveTheme(theme: Boolean) =
        withContext(NonCancellable +Dispatchers.IO) {
            sharedPrefs.edit()
                .remove("Theme")
                .putBoolean("Theme", theme)
                .apply()
        }

}
