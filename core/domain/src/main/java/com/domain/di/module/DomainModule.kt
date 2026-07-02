package com.domain.di.module

import com.domain.usecase.DiscoverByGenreUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import com.domain.usecase.SearchMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPopularMoviesUseCase(repository = get()) }
    factory { GetGenresUseCase(repository = get()) }
    factory { SearchMoviesUseCase(repository = get()) }
    factory { DiscoverByGenreUseCase(repository = get()) }
}