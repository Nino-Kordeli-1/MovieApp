package com.data.datasource

import com.data.dto.genre.GenreListResponseDto
import com.data.dto.movie.MovieResponseDto
import retrofit2.Response

interface SearchAndGenreDatasource {
    suspend fun getGenres(
    ): Response<GenreListResponseDto>

    suspend fun discoverByGenre(
        genreId: Int, page: Int
    ): Response<MovieResponseDto>

    suspend fun searchMovies(
        query: String, page: Int
    ): Response<MovieResponseDto>
}