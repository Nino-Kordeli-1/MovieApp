package com.domain.repository

import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(
        page: Int
    ): Flow<NetworkResult<List<MovieResponse>>>
}