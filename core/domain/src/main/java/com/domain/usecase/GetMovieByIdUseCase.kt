package com.domain.usecase

import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieByIdUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<NetworkResult<MovieResponse>> {
        return repository.getMovieById(movieId)
    }
}