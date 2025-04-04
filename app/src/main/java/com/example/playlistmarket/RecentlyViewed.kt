package com.example.playlistmarket

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.util.LinkedList


class RecentlyViewed (var context: Context) {
    private val linkedList : LinkedList<DataMusic> = LinkedList()
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        PRACTICUM_EXAMPLE_PREFERENCES,
        Context.MODE_PRIVATE
    )
    init{loadInPhone()}
    fun dataMusic(): List<DataMusic>{return linkedList}
    fun isEmpty(): Boolean = linkedList.isEmpty()
    fun addItem(item: DataMusic) {
        linkedList.remove(item)

        if (linkedList.size >= 5) {
            linkedList.removeLast()
        }

        linkedList.push(item)
        saveInPhone()
    }

    private fun loadInPhone(){
        linkedList.clear()
        for(i in 0..5){
            val json =  sharedPrefs.getString(i.toString(), null) ?: return
            linkedList.offerLast(Gson().fromJson(json, DataMusic::class.java))
        }

    }
    private fun saveInPhone() {
        clearload()
        clearPreferences()
        linkedList.forEachIndexed { index, item ->
            sharedPrefs.edit()
                .putString((index).toString(), Gson().toJson(item))
                .apply()
        }
    }
    private fun clearPreferences() {
        sharedPrefs.edit().clear().apply()
    }
    private fun clearload() {
        sharedPrefs.edit()
            .remove("0")
            .remove("1")
            .remove("2")
            .remove("3")
            .remove("4")
            .remove("5")
            .apply()
    }
    fun clearPreferencesAll() {
        clearload()
        clearPreferences()
        linkedList.clear()
    }

}

