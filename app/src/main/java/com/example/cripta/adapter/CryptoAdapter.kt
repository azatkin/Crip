package com.example.cripta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cripta.R

data class CryptoViewModel(val name: String, val symbol: String, val price: String)

class CryptoAdapter(private val cryptoList: List<CryptoViewModel>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.crypto_name)
        val symbolTextView: TextView = view.findViewById(R.id.crypto_symbol)
        val priceTextView: TextView = view.findViewById(R.id.crypto_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crypto, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.nameTextView.text = crypto.name
        holder.symbolTextView.text = crypto.symbol
        holder.priceTextView.text = crypto.price
    }

    override fun getItemCount(): Int = cryptoList.size
}