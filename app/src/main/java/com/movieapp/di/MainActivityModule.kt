package com.movieapp.di

import com.movieapp.vm.MainActivityViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val MainActivityModule = module {
    viewModel {
        MainActivityViewModel(
            connectivityObserver = get()
        )
    }
}