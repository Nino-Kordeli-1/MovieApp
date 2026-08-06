package com.domain.usecase

import com.common.resource.NetworkResult
import com.domain.model.MovieDetails
import com.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieByIdUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<NetworkResult<MovieDetails>> {
        return repository.getMovieById(movieId)
    }
}