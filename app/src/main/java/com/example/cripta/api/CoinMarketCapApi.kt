package com.example.cripta.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers


data class CryptoResponse(
    val data: List<CryptoItem>
)

data class CryptoItem(
    val id: String,
    val name: String,
    val symbol: String,
    val quote: Map<String, Quote>
)

data class Quote(
    val price: Double
)

interface CoinMarketCapApi {
    @Headers("X-CMC_PRO_API_KEY: ef59476b-e6f7-46d0-8e7c-f66fb8c84d1f")
    @GET("v1/cryptocurrency/listings/latest")
    fun getCryptocurrencies(): Call<CryptoResponse>
}