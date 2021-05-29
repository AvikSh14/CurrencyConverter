package com.example.currencyconverter.model

import com.google.gson.annotations.SerializedName


data class CurrencyListResponseDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("terms")
    val terms: String,

    @SerializedName("privacy")
    val privacy: String,

    @SerializedName("currencies")
    val currencies: HashMap<String, String>
)