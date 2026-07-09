package com.data.datasource

import com.data.dto.movie.MovieResponseDto
import retrofit2.Response

interface MovieDatasource {
    suspend fun getPopularMovies(
        page: Int
    ): Response<MovieResponseDto>
}