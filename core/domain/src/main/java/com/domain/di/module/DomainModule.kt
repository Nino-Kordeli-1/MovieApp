package com.domain.di.module

import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPopularMoviesUseCase(repository = get()) }
    factory { GetGenresUseCase(repository = get()) }
}