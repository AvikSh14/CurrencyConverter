package com.example.currencyconverter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.interfaces.CurrencyConverter
import com.example.currencyconverter.model.ExchangeRate
import kotlinx.android.synthetic.main.currency_row_item.view.*

class CurrencyAdapter(private final val currencyConverter: CurrencyConverter, private val exchangeRates: ArrayList<ExchangeRate>)
    : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(exchangeRate: ExchangeRate, currencyConverter: CurrencyConverter) {
            itemView.apply {
                tvCurrencyName.text = exchangeRate.exchangeCurrencies
                tvCurrencyRate.text = exchangeRate.exchangeRate.toString()
                setOnClickListener {
                    val prevCurrency = exchangeRate.exchangeCurrencies.substring(0,3)
                    val convertedCurrency = exchangeRate.exchangeCurrencies.substring(3,6)
                    currencyConverter.convert(prevCurrency, convertedCurrency, exchangeRate.exchangeRate)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.currency_row_item, parent, false))

    override fun getItemCount(): Int = exchangeRates.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
       holder.bind(exchangeRates[position], currencyConverter)
    }

    fun addExchangeRates(exchangeRates: List<ExchangeRate>) {
        this.exchangeRates.apply {
            clear()
            addAll(exchangeRates)
        }
    }
}