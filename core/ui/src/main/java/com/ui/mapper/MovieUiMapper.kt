package com.ui.mapper

import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.model.MovieUiModel
import kotlin.collections.map

fun movieUiMapper(
    movies: List<MovieResponse>,
    genres: List<GenreResponse>,
    favoriteIds: Set<Int>
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
            voteAverage = movie.voteAverage,
            isFavorite = movie.id in favoriteIds
        )
    }
}