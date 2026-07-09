package com.domain.usecase

import com.domain.repository.FavoriteRepository

class GetFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke() = repository.getFavorites()
}