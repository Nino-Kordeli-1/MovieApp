package com.domain.usecase

import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(page: Int): Flow<NetworkResult<List<MovieResponse>>> {
        return repository.getMovies(page)
    }
}