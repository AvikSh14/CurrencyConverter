package com.example.currencyconverter.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://api.currencylayer.com/";
    private const val API_KEY = "6f57f332fc288c4e6ea8af7ad9a01816"

    private fun getRetrofit() : Retrofit {
//        val interceptor = AuthInterceptor(API_KEY)
//        val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
        //client.interceptors().add(interceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
          //  .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}