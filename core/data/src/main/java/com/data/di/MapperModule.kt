package com.data.di

import com.data.mapper.GenreMapper
import com.data.mapper.MovieMapper
import org.koin.dsl.module

val mapperModule = module {
    single { MovieMapper() }
    single { GenreMapper() }
}