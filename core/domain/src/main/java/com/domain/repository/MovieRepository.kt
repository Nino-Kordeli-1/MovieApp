package com.domain.repository

import com.common.resource.NetworkResult
import com.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int = 1): NetworkResult<List<Movie>>
}