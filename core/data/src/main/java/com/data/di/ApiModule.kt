package com.data.di

import com.data.remote.PopularMovieApi
import com.data.remote.SearchAndGenreApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<PopularMovieApi> {
        get<Retrofit>().create(PopularMovieApi::class.java)
    }
    single<SearchAndGenreApi> {
        get<Retrofit>().create(SearchAndGenreApi::class.java)
    }
}