package com.ui.mapper

import com.domain.model.MovieDetails
import com.model.MovieDetailsUiModel
import com.model.MovieGenreUiModel

fun movieDetailsUiMapper(
    movie: MovieDetails,
    isFavorite: Boolean
): MovieDetailsUiModel {

    return MovieDetailsUiModel(
        id = movie.id,
        backdropPath = movie.backdropPath,
        genres = movie.genres.map { MovieGenreUiModel(id = it.id, name = it.name) },
        originalTitle = movie.originalTitle,
        overview = movie.overview,
        posterPath = movie.posterPath,
        releaseDate = movie.releaseDate,
        title = movie.title,
        voteAverage = movie.voteAverage,
        runtime = movie.runtime,
        isFavorite = isFavorite
    )
}