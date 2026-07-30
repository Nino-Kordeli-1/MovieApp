package com.model

data class MovieDetailsUiModel(
    val id: Int,
    val backdropPath: String,
    val genres: List<MovieGenreUiModel>,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val runtime: Int?,
    val isFavorite: Boolean = false
)

data class MovieGenreUiModel(
    val id: Int,
    val name: String
)