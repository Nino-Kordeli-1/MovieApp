package com.impl.di

import com.impl.screen.home.vm.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            getPopularMoviesUseCase = get(),
            getGenresUseCase = get(),
            searchMoviesUseCase = get(),
            discoverByGenreUseCase = get()
        )
    }
}