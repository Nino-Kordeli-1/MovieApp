package com.movieapp

import android.app.Application
import com.data.di.apiModule
import com.data.di.homeModule
import com.data.di.dataModule
import com.data.di.dataSourceModule
import com.data.di.mapperModule
import com.data.di.repositoryModule
import com.domain.di.module.domainModule
import com.impl.screen.splash.module.splashModule
import com.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            modules(
                dataModule,
                domainModule,
                homeModule,
                splashModule,
                networkModule,
                apiModule,
                repositoryModule,
                dataSourceModule,
                mapperModule
            )
        }
    }
}