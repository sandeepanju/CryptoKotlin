package com.example.cryptokotlin.di

import android.app.Application
import com.example.cryptokotlin.api.ApiService
import com.example.cryptokotlin.api.MainRepository
import com.example.cryptokotlin.utils.Constants
import com.example.cryptokotlin.viewModel.MainViewModel

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val  viewModelModule = module {
    single { MainViewModel(get())}
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    single { provideUserApi(get()) }
}

val netModule = module{

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder().cache(cache)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.interceptors().add(httpLoggingInterceptor)
        return httpBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(api: ApiService) = MainRepository(api)

    single { provideUserRepository(get()) }
}