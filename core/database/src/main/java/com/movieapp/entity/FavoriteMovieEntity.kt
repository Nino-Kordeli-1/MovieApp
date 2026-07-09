package com.movieapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(

    @PrimaryKey
    val id: Int,
    val popularity: Double,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val favoritedAt: Long = System.currentTimeMillis()
)