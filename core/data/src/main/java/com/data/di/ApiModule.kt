package com.data.di

import com.data.remote.PopularMovieApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<PopularMovieApi> {
        get<Retrofit>().create(PopularMovieApi::class.java)
    }
}