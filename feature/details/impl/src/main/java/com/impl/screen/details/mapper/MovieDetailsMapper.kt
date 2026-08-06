package com.impl.screen.details.mapper

import com.common.mapper.BaseMapper
import com.domain.model.MovieDetails
import com.model.MovieDetailsUiModel
import com.model.MovieGenreUiModel

class MovieDetailsUiMapper : BaseMapper<MovieDetailsUiMapperInput, MovieDetailsUiModel> {
    override fun map(from: MovieDetailsUiMapperInput): MovieDetailsUiModel {
        val movie = from.movie
        with(movie) {
            return MovieDetailsUiModel(
                id = id,
                backdropPath = backdropPath,
                genres = genres.map { MovieGenreUiModel(id = it.id, name = it.name) },
                originalTitle = originalTitle,
                overview = overview,
                posterPath = posterPath,
                releaseDate = releaseDate,
                title = title,
                voteAverage = voteAverage,
                runtime = runtime,
                isFavorite = from.isFavorite,
            )
        }
    }
}

data class MovieDetailsUiMapperInput(
    val movie: MovieDetails,
    val isFavorite: Boolean
)