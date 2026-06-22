package com.data.di

import com.domain.usecase.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPopularMoviesUseCase(repository = get()) }
}