package com.example.currencyconverter.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.api.ApiHelper
import com.example.currencyconverter.repositories.CurrencyRepository
import com.example.currencyconverter.viewmodels.CurrencyViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(CurrencyRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}