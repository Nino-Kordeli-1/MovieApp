package com.impl.mapper

import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.impl.screen.home.model.MovieUiModel

fun movieUiMapper(
    movies: List<MovieResponse>,
    genres: List<GenreResponse>
): List<MovieUiModel> {
    return movies.map { movie ->
        val genreNames = movie.genreIds.mapNotNull { id ->
            genres.firstOrNull { it.id == id }?.name
        }
        MovieUiModel(
            id = movie.id,
            popularity = movie.popularity,
            backdropPath = movie.backdropPath,
            genre = genreNames.firstOrNull() ?: "Unknown",
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            title = movie.title,
            voteAverage = movie.voteAverage
        )
    }
}