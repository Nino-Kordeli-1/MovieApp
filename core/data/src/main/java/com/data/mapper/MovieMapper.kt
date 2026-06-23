package com.data.mapper

import com.data.dto.movie.MovieDto
import com.data.dto.movie.MovieResponseDto
import com.domain.model.MovieResponse

fun MovieResponseDto.toDomain() = results.map { it.toDomain() }

fun MovieDto.toDomain(): MovieResponse {
    return MovieResponse(
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