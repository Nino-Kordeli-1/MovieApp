package com.data.mapper

import com.data.dto.movie.MovieDto
import com.data.dto.movie.MovieResponseDto
import com.domain.model.Movie

fun MovieResponseDto.toDomain() = results.map { it.toDomain() }

fun MovieDto.toDomain(): Movie {
    return Movie(
        backdropPath = backdropPath.orEmpty(),
        genreIds = genreIds,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        id = id,
        popularity = popularity
    )
}