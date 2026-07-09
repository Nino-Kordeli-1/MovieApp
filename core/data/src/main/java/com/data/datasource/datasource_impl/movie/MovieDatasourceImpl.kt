package com.data.datasource.datasource_impl.movie

import com.data.datasource.MovieDatasource
import com.data.dto.movie.MovieDto
import com.data.dto.movie.MovieResponseDto
import com.data.remote.PopularMovieApi
import retrofit2.Response

class MovieDatasourceImpl(
    private val api: PopularMovieApi
) : MovieDatasource {
    override suspend fun getPopularMovies(
        page: Int
    ): Response<MovieResponseDto> {
        return api.getPopularMovies(page)
    }

    override suspend fun getMovieById(
        movieId: Int
    ): Response<MovieDto> {
        return api.getMovieById(movieId)
    }
}