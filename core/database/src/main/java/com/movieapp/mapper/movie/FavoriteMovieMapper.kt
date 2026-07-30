package com.movieapp.mapper.movie

import com.common.mapper.BaseMapper
import com.domain.model.MovieResponse
import com.movieapp.entity.FavoriteMovieEntity

class FavoriteMovieMapper : BaseMapper<MovieResponse, FavoriteMovieEntity> {
    override fun map(from: MovieResponse): FavoriteMovieEntity {
        with(from) {
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
    }
}