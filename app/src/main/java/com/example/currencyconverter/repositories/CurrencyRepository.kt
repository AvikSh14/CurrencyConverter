package com.example.currencyconverter.repositories

import com.example.currencyconverter.api.ApiHelper

class CurrencyRepository(private val apiHelper: ApiHelper) {
    suspend fun getCurrencyList() = apiHelper.getCurrencyList()

    suspend fun getExchangeRate(currency: String) = apiHelper.getExchangeRate(currency)
}