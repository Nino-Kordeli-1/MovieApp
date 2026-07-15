package com.impl.screen.home.model

data class MovieUiModel(
    val id: Int,
    val popularity: Double,
    val backdropPath: String,
    val genre: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double
)