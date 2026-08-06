package com.domain.repository

import com.common.resource.NetworkResult
import com.domain.model.GenreResponse
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenres(): Flow<NetworkResult<List<GenreResponse>>>
}