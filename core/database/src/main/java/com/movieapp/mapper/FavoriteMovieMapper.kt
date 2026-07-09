package com.movieapp.mapper

import com.domain.model.MovieResponse
import com.movieapp.entity.FavoriteMovieEntity

fun MovieResponse.toEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        popularity = popularity,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}

fun FavoriteMovieEntity.toDomain(): MovieResponse {
    return MovieResponse(
        id = id,
        popularity = popularity,
        backdropPath = backdropPath,
        genreIds = genreIds,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}