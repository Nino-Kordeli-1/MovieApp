package com.domain.model

data class Movie(
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
)