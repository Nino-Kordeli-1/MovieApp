package com.impl.screen.home.mapper

import com.common.mapper.BaseMapper
import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.model.MovieUiModel
import kotlin.collections.map

class MovieUiMapper : BaseMapper<MovieUiMapperInput, List<MovieUiModel>> {
    override fun map(from: MovieUiMapperInput): List<MovieUiModel> {
        return from.movies.map { movie ->

            val genreNames = movie.genreIds.mapNotNull { id ->
                from.genres.firstOrNull { genre ->
                    genre.id == id
                }?.name
            }
            with(movie) {
                MovieUiModel(
                    id = id,
                    popularity = popularity,
                    backdropPath = backdropPath,
                    genre = genreNames.firstOrNull() ?: "Unknown",
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    title = title,
                    voteAverage = voteAverage,
                    isFavorite = id in from.favoriteIds
                )
            }
        }
    }
}

data class MovieUiMapperInput(
    val movies: List<MovieResponse>,
    val genres: List<GenreResponse>,
    val favoriteIds: Set<Int>
)