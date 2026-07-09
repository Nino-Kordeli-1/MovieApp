package com.data.datasource

import com.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface FavoritesDatasource {
    suspend fun insert(movie: MovieResponse)
    suspend fun delete(movieId: Int)
    fun getFavorites(): Flow<List<MovieResponse>>
    fun isFavorite(movieId: Int): Flow<Boolean>
}