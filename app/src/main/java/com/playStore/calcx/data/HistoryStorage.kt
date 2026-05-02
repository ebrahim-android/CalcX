package com.playStore.calcx.data

import android.content.Context
import android.provider.Settings.Global.getString
import com.google.gson.Gson
import com.playStore.calcx.domain.model.HistoryItem
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken

class HistoryStorage(context: Context) {

    private val prefs = context.getSharedPreferences("calc_prefs", Context.MODE_PRIVATE)
    private val gson  = Gson()

    private val KEY = "History"

    fun save(history: List<HistoryItem>){
        val json = gson.toJson(history)
        prefs.edit { putString(KEY, json) }
    }

    fun load(): MutableList<HistoryItem> {
        val json = prefs.getString(KEY, null)?: return mutableListOf()
        val type = object : TypeToken<MutableList<HistoryItem>> () {}.type
        return gson.fromJson(json, type)
    }
}