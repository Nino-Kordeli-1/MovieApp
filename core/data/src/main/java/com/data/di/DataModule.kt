package com.data.di

import com.data.repository.MovieRepositoryImpl
import com.domain.repository.MovieRepository
import org.koin.dsl.module

val dataModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            dataSource = get(),
            movieMapper = get()
        )
    }
}