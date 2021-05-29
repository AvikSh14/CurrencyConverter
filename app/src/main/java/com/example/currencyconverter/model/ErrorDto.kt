package com.example.currencyconverter.model


import com.google.gson.annotations.SerializedName

data class ErrorDto(
    val error: Error,
    val success: Boolean
)