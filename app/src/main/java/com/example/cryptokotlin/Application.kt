package com.example.cryptokotlin

import android.app.Application
import com.example.cryptokotlin.di.apiModule
import com.example.cryptokotlin.di.netModule
import com.example.cryptokotlin.di.repositoryModule
import com.example.cryptokotlin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule, netModule, apiModule))
        }
    }
}