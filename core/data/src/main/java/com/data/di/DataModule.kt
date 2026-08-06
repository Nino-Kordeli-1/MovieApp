package com.data.di

import com.data.repository.FavoriteRepositoryImpl
import com.domain.repository.FavoriteRepository
import org.koin.dsl.module

val dataModule = module {
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            dao = get(),
            favoriteMovieMapper = get(),
            favoriteMovieEntityMapper = get()
        )
    }
}