package com.example.currencyconverter.model


import com.google.gson.annotations.SerializedName

data class Error(
    val code: Int,
    val info: String,
    val type: String
)