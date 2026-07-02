package com.data.mapper

import com.data.dto.movie.MovieDto
import com.domain.model.MovieResponse

class MovieMapper : BaseMapper<MovieDto, MovieResponse> {
    override fun map(from: MovieDto): MovieResponse {
        return MovieResponse(
            backdropPath = from.backdropPath.orEmpty(),
            genreIds = from.genreIds,
            originalTitle = from.originalTitle,
            overview = from.overview,
            posterPath = "https://image.tmdb.org/t/p/w500${from.posterPath.orEmpty()}",
            releaseDate = from.releaseDate,
            title = from.title,
            voteAverage = from.voteAverage,
            id = from.id,
            popularity = from.popularity
        )
    }
}