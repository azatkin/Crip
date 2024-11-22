package com.example.cripta

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cripta.adapter.CryptoAdapter
import com.example.cripta.adapter.CryptoViewModel
import com.example.cripta.api.CryptoResponse
import com.example.cripta.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var cryptoAdapter: CryptoAdapter
    private val cryptoList = mutableListOf<CryptoViewModel>()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.crypto_recycler_view)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cryptoAdapter = CryptoAdapter(cryptoList)
        recyclerView.adapter = cryptoAdapter

        // Инициализация и установка слушателя для SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            fetchCryptocurrencies() // Загрузка данных при обновлении
        }

        fetchCryptocurrencies() // Загрузка данных при первом запуске
    }

    private fun fetchCryptocurrencies() {
        // Показать индикатор обновления
        swipeRefreshLayout.isRefreshing = true

        ApiClient.api.getCryptocurrencies().enqueue(object : Callback<CryptoResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<CryptoResponse>, response: Response<CryptoResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data ?: emptyList()
                    cryptoList.clear()
                    cryptoList.addAll(data.map { item ->
                        CryptoViewModel(
                            name = item.name,
                            symbol = item.symbol,
                            price = item.quote["USD"]?.price?.toString() ?: "N/A"
                        )
                    })
                    cryptoAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to fetch data: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Скрыть индикатор обновления
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<CryptoResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                // Скрыть индикатор обновления в случае ошибки
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}
