package com.impl.screen.favorites.di

import com.impl.screen.favorites.vm.FavoritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {

    viewModel {
        FavoritesViewModel(
            getFavoriteUseCase = get(),
            getGenresUseCase = get(),
            removeFavoriteUseCase = get()
        )
    }
}