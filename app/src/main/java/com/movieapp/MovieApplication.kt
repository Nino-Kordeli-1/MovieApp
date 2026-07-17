package com.movieapp

import android.app.Application
import com.data.di.coreModules
import com.data.di.dataModule
import com.data.di.observerModule
import com.impl.di.detailsModule
import com.impl.di.homeModule
import com.impl.screen.favorites.di.favoritesModule
import com.impl.screen.splash.module.splashModule
import com.movieapp.di.databaseModule
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
                    databaseModule,
                    dataModule,
                    favoritesModule,
                    detailsModule,
                    observerModule
                )
            )
        }
    }
}