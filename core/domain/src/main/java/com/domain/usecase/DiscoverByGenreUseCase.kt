package com.domain.usecase

import com.domain.repository.SearchAndGenreRepository

class DiscoverByGenreUseCase(
    private val repository: SearchAndGenreRepository
) {
    suspend operator fun invoke(genreId: Int, page: Int) = repository.discoverByGenre(
        genreId = genreId, page = page
    )
}