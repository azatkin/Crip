package com.example.cripta.network

import com.example.cripta.api.CoinMarketCapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private const val BASE_URL = "https://pro-api.coinmarketcap.com/"

    val api: CoinMarketCapApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapApi::class.java)
    }
}