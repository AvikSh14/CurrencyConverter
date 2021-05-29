package com.example.currencyconverter.api

class ApiHelper(private val apiService: ApiService) {
    private val API_KEY = "6f57f332fc288c4e6ea8af7ad9a01816"

    suspend fun getCurrencyList() = apiService.getCurrencyList(API_KEY)

    suspend fun getExchangeRate(currency: String) = apiService.getExchangeRatesForCurrency(API_KEY, currency)
}