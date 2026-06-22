package com.data.repository

import com.common.resource.NetworkResult
import com.data.mapper.toDomain
import com.data.remote.PopularMovieApi
import com.domain.model.Movie
import com.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: PopularMovieApi
) : MovieRepository {

    override suspend fun getMovies(page: Int): NetworkResult<List<Movie>> {
        return try {
            val response = api.getPopularMovies(page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map { it.toDomain() } ?: emptyList()
                NetworkResult.Success(movies)
            } else {
                NetworkResult.Error("Failed to load movies: ${response.code()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error", e)
        }
    }
}