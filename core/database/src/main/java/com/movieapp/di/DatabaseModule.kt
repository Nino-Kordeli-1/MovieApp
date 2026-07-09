package com.movieapp.di

import androidx.room.Room
import com.movieapp.database.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            "movie_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single {
        get<MovieDatabase>().favoriteMovieDao()
    }
}