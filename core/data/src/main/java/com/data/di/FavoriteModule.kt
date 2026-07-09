package com.data.di

import com.data.repository.FavoriteRepositoryImpl
import com.domain.repository.FavoriteRepository
import org.koin.dsl.module

val favoriteModule = module {
    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            dataSource = get()
        )
    }
}
