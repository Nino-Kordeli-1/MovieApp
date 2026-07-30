package com.data.dto.movie

import com.data.dto.genre.GenreResponseDto
import com.domain.model.GenreResponse
import com.domain.model.MovieDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    val genres: List<GenreResponseDto> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String,
    val runtime: Int? = null,
    val title: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        backdropPath = backdropPath.orEmpty(),
        genres = genres.map { GenreResponse(id = it.id, name = it.name) },
        originalTitle = originalTitle,
        overview = overview,
        posterPath = "https://image.tmdb.org/t/p/w500${posterPath.orEmpty()}",
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        runtime = runtime
    )
}