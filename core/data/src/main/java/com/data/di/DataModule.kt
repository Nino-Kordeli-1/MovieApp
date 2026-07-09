package com.data.di

import com.data.datasource.FavoritesDatasource
import com.data.datasource.datasource_impl.favorites.FavoritesDatasourceImpl
import com.data.repository.FavoriteRepositoryImpl
import com.domain.repository.FavoriteRepository
import org.koin.dsl.module

val dataModule = module {

    single<FavoritesDatasource> {
        FavoritesDatasourceImpl(
            dao = get()
        )
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(
            dataSource = get()
        )
    }
}