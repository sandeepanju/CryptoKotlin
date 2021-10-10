package com.example.cryptokotlin.api

import com.example.cryptokotlin.pojo.GenericResponse
import com.example.cryptokotlin.pojo.MData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val api: ApiService) {
    lateinit var data: GenericResponse<List<MData>>

    suspend fun getCryptoData(): List<MData> {
        withContext(Dispatchers.IO) {
            data = api.getCryptoData()
        }
        return data.data
    }
}