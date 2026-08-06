package com.domain.model

data class MovieResponse(
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
    val runtime: Int? = null
)