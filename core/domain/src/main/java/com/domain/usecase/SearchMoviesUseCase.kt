package com.domain.usecase

import com.domain.repository.SearchAndGenreRepository

class SearchMoviesUseCase(
    private val repository: SearchAndGenreRepository
) {
    suspend operator fun invoke(query: String, page: Int) = repository.searchMovies(
        query = query, page = page
    )
}
