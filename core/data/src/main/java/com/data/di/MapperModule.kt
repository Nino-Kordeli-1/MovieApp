package com.data.di

import com.data.mapper.GenreMapper
import com.data.mapper.MovieMapper
import com.movieapp.mapper.entity.FavoriteMovieEntityMapper
import com.movieapp.mapper.movie.FavoriteMovieMapper
import org.koin.dsl.module

val mapperModule = module {
    single { MovieMapper() }
    single { GenreMapper() }
    single { FavoriteMovieMapper() }
    single { FavoriteMovieEntityMapper() }
    single { MovieMapper() }
}