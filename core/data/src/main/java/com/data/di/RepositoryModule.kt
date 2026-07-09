package com.data.di

import com.data.repository.GenreRepositoryImpl
import com.data.repository.MovieRepositoryImpl
import com.data.repository.SearchAndGenreRepositoryImpl
import com.domain.repository.GenreRepository
import com.domain.repository.MovieRepository
import com.domain.repository.SearchAndGenreRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            dataSource = get(),
            movieMapper = get()
        )
    }

    single<GenreRepository> {
        GenreRepositoryImpl(
            dataSource = get(),
            genreMapper = get(),
        )
    }

    single<SearchAndGenreRepository> {
        SearchAndGenreRepositoryImpl(
            dataSource = get(),
            movieMapper = get()
        )
    }
}