package com.domain.usecase

import com.common.resource.NetworkResult
import com.domain.model.Movie
import com.domain.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): NetworkResult<List<Movie>> {
        return repository.getMovies(page)
    }
}