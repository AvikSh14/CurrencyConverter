package com.example.currencyconverter.model


import com.google.gson.annotations.SerializedName

data class ExchangeRateResponseDto(
    val privacy: String,
    val quotes: HashMap<String, String>,
    val source: String,
    val success: Boolean,
    val terms: String,
    val timestamp: Int
)