package com.data.remote

import com.data.dto.genre.GenreListResponseDto
import com.data.dto.movie.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAndGenreApi {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>

    @GET("genre./movie/list")
    suspend fun getGenres(): Response<GenreListResponseDto>

    @GET("discover/movie")
    suspend fun discoverByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ): Response<MovieResponseDto>
}