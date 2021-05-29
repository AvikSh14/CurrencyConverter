package com.example.currencyconverter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.adapters.CurrencyAdapter
import com.example.currencyconverter.api.ApiHelper
import com.example.currencyconverter.api.RetrofitBuilder
import com.example.currencyconverter.interfaces.CurrencyConverter
import com.example.currencyconverter.model.CurrencyListResponseDto
import com.example.currencyconverter.model.ExchangeRate
import com.example.currencyconverter.model.ExchangeRateResponseDto
import com.example.currencyconverter.utils.SharedPrefManager
import com.example.currencyconverter.utils.Status
import com.example.currencyconverter.utils.ViewModelFactory
import com.example.currencyconverter.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CurrencyConverter, AdapterView.OnItemSelectedListener {

    private lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var exchangeRates: ArrayList<ExchangeRate>
    private lateinit var currencyAdapter: CurrencyAdapter
    lateinit var currencies : Array<String>
    private val TAG = "MainActivity"
    //define map to store exchange rates
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currencyViewModel = ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)))
            .get(CurrencyViewModel::class.java)

        fetchCurrencyList()
        setupUI()
    }

    private fun setupUI() {
        spinnerCurrencyList.onItemSelectedListener = this

        recyclerView.layoutManager = LinearLayoutManager(this)
        currencyAdapter = CurrencyAdapter(this, arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = currencyAdapter
    }

    private fun fetchCurrencyList() {
        val prefKey: String = getString(R.string.pref_currency_list)
        val currencyResponseDto: CurrencyListResponseDto? = SharedPrefManager.getObject<CurrencyListResponseDto>(prefKey)
        if(currencyResponseDto == null) {
            currencyViewModel.getCurrencyList().observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            val currencyResponseDto: CurrencyListResponseDto = resource.data as CurrencyListResponseDto
                            if (currencyResponseDto.success) {
                                SharedPrefManager.putObject(currencyResponseDto, prefKey)
                                getCurrencies(currencyResponseDto)
                            }
                            Log.i(TAG, "success")
                            Log.i(TAG, "Map size : " + currencyResponseDto.currencies.size)
                        }

                        Status.ERROR -> {
                            Log.i(TAG, resource.message ?: "error")
                        }

                        Status.LOADING -> {
                            Log.i(TAG, "loading")
                        }

                        Status.FAILURE -> {
                            Log.i(TAG, resource.message ?: "failure")
                        }
                    }
                }
            })
        } else {
            makeText(this, "Getting currencies from sharedpref", Toast.LENGTH_LONG).show()
            getCurrencies(currencyResponseDto)
        }
    }

    fun getCurrencies(currencyListResponseDto: CurrencyListResponseDto) {
        currencies = currencyListResponseDto.currencies.keys.toTypedArray()
        currencies.sort()
        ArrayAdapter(this,
            R.layout.currency_spinner_item, currencies)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCurrencyList.adapter = adapter
            }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        makeText(this, currencies[position], Toast.LENGTH_LONG).show()
        var key: String = currencies[position] + getString(R.string.pref_exchange_rate_response_dto)
        var exchangeRateResponseDto: ExchangeRateResponseDto? = SharedPrefManager.getObject<ExchangeRateResponseDto>(key)

        if(exchangeRateResponseDto==null) {
            makeText(this, "will get from api call", Toast.LENGTH_LONG).show()
            fetchLiveCurrencyRate(position, key)
        } else {
            makeText(this, "Get from sharedpref", Toast.LENGTH_LONG).show()

            getExchangeRates(exchangeRateResponseDto)

        }
    }

    private fun fetchLiveCurrencyRate(position: Int, key: String) {

        currencyViewModel.getExchangeRate(currencies[position]).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        var exchangeRateResponseDto: ExchangeRateResponseDto =
                            resource.data as ExchangeRateResponseDto

                        if (exchangeRateResponseDto.success) {
                            //store this object to sharedpref
                            SharedPrefManager.putObject(exchangeRateResponseDto, key)
                            getExchangeRates(exchangeRateResponseDto)
                        } else {
                            //not success
                            makeText(
                                this,
                                "Error occurred during fetch",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    Status.ERROR -> {
                        Log.i(TAG, resource.message ?: "error")
                    }

                    Status.LOADING -> {
                        Log.i(TAG, "loading")
                    }

                    Status.FAILURE -> {
                        Log.i(TAG, resource.message ?: "failure")
                    }
                }
            }
        })
    }

    private fun getExchangeRates(exchangeRateResponseDto: ExchangeRateResponseDto) {
        exchangeRates = ArrayList()
        for ((key, value) in exchangeRateResponseDto.quotes) {
            var exchangeRate = ExchangeRate(key, value.toDouble())
            exchangeRates.add(exchangeRate)
        }
        exchangeRates.sortedBy { exchangeRate -> exchangeRate.exchangeCurrencies }
        retrieveList(exchangeRates)
    }

    private fun retrieveList(exchangeRates: ArrayList<ExchangeRate>) {
        currencyAdapter.apply {
            addExchangeRates(exchangeRates)
            notifyDataSetChanged()
        }
    }

    override fun convert(prevCurrency: String, convertedCurrency: String, rate: Double) {
        var convertedValue: Double = 0.0
        try {
            val inputAmount = etAmount.text.toString().toDouble()
            convertedValue = inputAmount * rate
        } catch (exception: NumberFormatException) {

        }
        tvConvertedValue.text = etAmount.text.toString() + " " + prevCurrency + " = " + convertedValue + " " + convertedCurrency
    }

}