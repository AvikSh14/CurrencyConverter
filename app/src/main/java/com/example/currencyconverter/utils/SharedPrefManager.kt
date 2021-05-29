package com.example.currencyconverter.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object SharedPrefManager {

    lateinit var sharedPreferences: SharedPreferences

    private const val PREF_NAME = "CURRENCY_CONVERTER_PREF"

    fun with(application: Application) {
        sharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun <T> putObject(obj: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(obj)
        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> getObject(key: String) : T? {
        val value = sharedPreferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun setLastLiveCurrencyFetchTime(key: String, time: Long) {
        sharedPreferences.edit().putLong(key, time).apply()
    }

    fun getLastLiveCurrencyFetchTime(key: String) : Long {
        return sharedPreferences.getLong(key, System.currentTimeMillis())
    }
}