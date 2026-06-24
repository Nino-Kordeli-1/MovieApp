package com.data.di

import com.data.datasource.MovieDatasource
import com.data.datasource.SearchAndGenreDatasource
import com.data.datasource.datasource_impl.movie.MovieDatasourceImpl
import com.data.datasource.datasource_impl.search_and_genre.SearchAndGenreDatasourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<MovieDatasource> {
        MovieDatasourceImpl(
            api = get()
        )
    }

    single<SearchAndGenreDatasource> {
        SearchAndGenreDatasourceImpl(
            api = get()
        )
    }
}
