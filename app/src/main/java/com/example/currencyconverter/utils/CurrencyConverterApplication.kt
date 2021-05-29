package com.example.currencyconverter.utils

import android.app.Application

class CurrencyConverterApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefManager.with(this)
    }
}