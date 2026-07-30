package com.domain.usecase

import com.domain.repository.FavoriteRepository

class IsFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(movie: Int) =
        repository.isFavorite(movie)
}