package com.movieapp.mapper.entity

import com.common.mapper.BaseMapper
import com.domain.model.MovieResponse
import com.movieapp.entity.FavoriteMovieEntity

class FavoriteMovieEntityMapper : BaseMapper<FavoriteMovieEntity, MovieResponse> {
    override fun map(from: FavoriteMovieEntity): MovieResponse {
        with(from) {
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
                voteAverage = voteAverage,
                runtime = null
            )
        }
    }

}