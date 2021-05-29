package com.example.currencyconverter.interfaces

interface CurrencyConverter {
    fun convert(prevCurrency: String, convertedCurrency: String, rate: Double)
}