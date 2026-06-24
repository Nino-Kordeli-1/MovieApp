package com.domain.repository

import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface SearchAndGenreRepository {
    suspend fun searchMovies(query: String, page: Int): Flow<NetworkResult<List<MovieResponse>>>
    suspend fun discoverByGenre(genreId: Int, page: Int): Flow<NetworkResult<List<MovieResponse>>>
}