package com.domain.repository

import com.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addFavorite(movie: MovieResponse)
    suspend fun removeFavorite(movieId: Int)
    fun getFavorites(): Flow<List<MovieResponse>>
    fun isFavorite(movieId: Int): Flow<Boolean>
}