package com.data.di

import com.data.repository.MovieRepositoryImpl
import com.domain.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            api = get()
        )
    }
}