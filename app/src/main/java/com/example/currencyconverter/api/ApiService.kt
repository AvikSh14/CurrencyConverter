package com.example.currencyconverter.api

import com.example.currencyconverter.model.CurrencyListResponseDto
import com.example.currencyconverter.model.ExchangeRateResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list")
    suspend fun getCurrencyList(@Query("access_key") apiKey: String) : Response<CurrencyListResponseDto>

    @GET("live")
    suspend fun getExchangeRatesForCurrency(@Query("access_key") apiKey: String, @Query("source") source: String)
        : Response<ExchangeRateResponseDto>
}