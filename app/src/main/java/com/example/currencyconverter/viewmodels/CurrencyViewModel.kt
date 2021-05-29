package com.example.currencyconverter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.currencyconverter.model.CurrencyListResponseDto
import com.example.currencyconverter.model.ExchangeRateResponseDto
import com.example.currencyconverter.repositories.CurrencyRepository
import com.example.currencyconverter.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CurrencyViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {
    fun getCurrencyList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = currencyRepository.getCurrencyList()
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body() as CurrencyListResponseDto))
            } else
                emit(Resource.error(data = response.errorBody(), message = response.message()))
        } catch (exception: Exception) {
            emit(
                Resource.failure(
                    data = null,
                    message = exception.message ?: "Unexpected error occurred"
                )
            )
        }
    }

    fun getExchangeRate(currency: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = currencyRepository.getExchangeRate(currency)
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body() as ExchangeRateResponseDto))
            } else {
                emit(Resource.error(data = response.errorBody(), message = response.message()))
            }
        } catch (exception: Exception) {
            emit(
                Resource.failure(
                    data = null,
                    message = exception.message ?: "Unexpected error occurred"
                )
            )
        }
    }
}