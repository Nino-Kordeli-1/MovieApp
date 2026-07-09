package com.domain.usecase

import com.common.resource.NetworkResult
import com.domain.model.GenreResponse
import com.domain.repository.GenreRepository
import kotlinx.coroutines.flow.Flow

class GetGenresUseCase(
    private val repository: GenreRepository
) {
    operator fun invoke(): Flow<NetworkResult<List<GenreResponse>>> {
        return repository.getGenres()
    }
}