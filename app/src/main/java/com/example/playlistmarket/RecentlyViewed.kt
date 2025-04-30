package com.example.playlistmarket

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.LinkedList


class RecentlyViewed (var context: Context) {

    private val maxListSize :Int = 10
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

        if (linkedList.size >= maxListSize) {
            linkedList.removeLast()
        }

        linkedList.push(item)
        saveInPhone()
    }

    private fun loadInPhone(){
        linkedList.clear()
        for(i in 0..maxListSize){
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
        val clern = sharedPrefs.edit()
        for( i in 0..maxListSize) {
            clern.remove(i.toString())
        }
        clern.apply()
    }
    fun clearPreferencesAll() {
        clearload()
        clearPreferences()
        linkedList.clear()
    }

}

