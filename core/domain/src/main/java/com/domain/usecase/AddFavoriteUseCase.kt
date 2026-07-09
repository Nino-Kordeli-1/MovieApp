package com.domain.usecase

import com.domain.model.MovieResponse
import com.domain.repository.FavoriteRepository

class AddFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movie: MovieResponse) {
        repository.addFavorite(movie)
    }
}