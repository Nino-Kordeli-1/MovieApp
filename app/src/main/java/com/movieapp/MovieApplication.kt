package com.movieapp

import android.app.Application
import com.data.di.coreModules
import com.impl.di.homeModule
import com.impl.screen.splash.module.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            modules(
                coreModules + listOf(
                    homeModule,
                    splashModule,
                )
            )
        }
    }
}