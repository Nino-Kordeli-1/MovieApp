package com.domain.usecase

import com.domain.repository.FavoriteRepository

class RemoveFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int) {
        repository.removeFavorite(movieId)
    }
}