package com.data.di

import com.data.observer.NetworkConnectivityObserver
import com.domain.observer.ConnectivityObserver
import org.koin.dsl.module

val observerModule = module {
    single<ConnectivityObserver> {
        NetworkConnectivityObserver(get())
    }
}