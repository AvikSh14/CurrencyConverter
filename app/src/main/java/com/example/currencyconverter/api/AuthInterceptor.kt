package com.example.currencyconverter.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(request()
            .newBuilder()
            .addHeader("access_key", apiKey)
            .build()
        )
    }
}