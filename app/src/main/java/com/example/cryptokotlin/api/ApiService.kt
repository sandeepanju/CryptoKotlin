package com.example.cryptokotlin.api

import com.example.cryptokotlin.pojo.GenericResponse
import com.example.cryptokotlin.pojo.MData
import retrofit2.http.GET

interface ApiService {
    @GET("assets?limit=20&offset=0")
    suspend fun getCryptoData(): GenericResponse<List<MData>>
}