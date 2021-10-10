package com.example.cryptokotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptokotlin.api.MainRepository
import com.example.cryptokotlin.pojo.MData
import com.example.cryptokotlin.pojo.ViewState
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val dataObserve: LiveData<ViewState<List<MData>>> get() = _dataObserve
    private val _dataObserve: MutableLiveData<ViewState<List<MData>>> = MutableLiveData()

    fun fetchCryptoData() {
        viewModelScope.launch {
            try {
                _dataObserve.postValue(ViewState.Loading)
                val data = repository.getCryptoData()
                _dataObserve.postValue(ViewState.Success(data))
            } catch (e: Exception) {
                if (e is IOException) _dataObserve.postValue(ViewState.NetworkError)
                else _dataObserve.postValue(ViewState.Error(e.message.toString()))
                e.printStackTrace()
            }
        }
    }
}