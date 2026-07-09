package com.data.repository

import com.data.datasource.FavoritesDatasource
import com.domain.model.MovieResponse
import com.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(
    private val dataSource: FavoritesDatasource
) : FavoriteRepository {
    override suspend fun addFavorite(movie: MovieResponse) {
        dataSource.insert(movie)
    }

    override suspend fun removeFavorite(movieId: Int) {
        dataSource.delete(movieId)
    }

    override fun getFavorites(): Flow<List<MovieResponse>> {
        return dataSource.getFavorites()
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return dataSource.isFavorite(movieId)
    }
}