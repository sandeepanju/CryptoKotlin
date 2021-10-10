package com.example.cryptokotlin.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.cryptokotlin.R
import com.example.cryptokotlin.adapter.CryptoAdapter
import com.example.cryptokotlin.databinding.ActivityMainBinding
import com.example.cryptokotlin.pojo.MData
import com.example.cryptokotlin.pojo.ViewState
import com.example.cryptokotlin.utils.Constants
import com.example.cryptokotlin.utils.hideKeyboard
import com.example.cryptokotlin.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private var isSearchActive = false
    private val cryptoAdapter by lazy { CryptoAdapter() }
    private var listData: ArrayList<MData> = ArrayList()
    private val mainViewModel by viewModel<MainViewModel>()
    private val binding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        ) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        callApi()
        binding.rvCrypto.adapter = cryptoAdapter
        observeData()
        searchParticularCrypto()
    }

    private fun searchParticularCrypto() {
        binding.etSearch.apply {
            doOnTextChanged { text, start, before, count ->
                /*---on basis of Flag update the Api calling after delay------
                * if text is empty then re-starting the Api calling */
                isSearchActive = if (text.isNullOrBlank()) {
                    hideKeyboard(this@MainActivity)
                    callApi()
                    false
                } else true
            }
            /*right now managing the search through search icon click only*/
            setOnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH && textView.text.toString()
                        .isNotEmpty()
                ) {
                    performSearch(textView.text.toString())
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun performSearch(query: String) {
        hideKeyboard(this)
        cryptoAdapter.setItems(listData.filter { mData -> mData.name.contains(query, true) })
    }

    private fun callApi() {
        mainViewModel.fetchCryptoData()
    }

    private fun observeData() {
        mainViewModel.dataObserve.apply {
            observe(this@MainActivity, Observer {
                when (it) {
                    is ViewState.Success -> {
                        Log.e("state--", "success")
                        listData.clear()
                        listData.addAll(it.data)
                        cryptoAdapter.setItems(it.data)
                        updateCryptoAfterDelay()
                    }
                    is ViewState.Loading -> {
                        Log.e("state--", "Loading")

                    }
                    is ViewState.Error -> {
                        Log.e("state--", "Error")

                    }
                    is ViewState.NetworkError -> {
                        Log.e("state--", "NetworkError")
                    }
                }
            })
        }
    }

    private fun updateCryptoAfterDelay() {
        Handler().postDelayed({
            /*----method run  after 10s delay if search flag is not active---*/
            if (!isSearchActive) callApi()
        }, Constants.DELAY_TIME)
    }
}