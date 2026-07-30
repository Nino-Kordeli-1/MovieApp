package com.domain.model

data class MovieDetails(
    val id: Int,
    val backdropPath: String,
    val genres: List<GenreResponse>,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val runtime: Int?
)