package com.impl.di

import com.impl.screen.home.mapper.MovieUiMapper
import com.impl.screen.home.vm.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { MovieUiMapper() }
    viewModel {
        HomeViewModel(
            getPopularMoviesUseCase = get(),
            getGenresUseCase = get(),
            searchMoviesUseCase = get(),
            discoverByGenreUseCase = get(),
            getFavoriteUseCase = get(),
            removeFavoriteUseCase = get(),
            addFavoriteUseCase = get(),
            connectivityObserver = get(),
            movieUiMapper = get()
        )
    }
}