package com.data.datasource.datasource_impl.favorites

import com.data.datasource.FavoritesDatasource
import com.domain.model.MovieResponse
import com.movieapp.dao.FavoriteMovieDao
import com.movieapp.mapper.toDomain
import com.movieapp.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesDatasourceImpl(
    private val dao: FavoriteMovieDao
) : FavoritesDatasource {
    override suspend fun insert(movie: MovieResponse) {
        dao.insert(movie.toEntity())
    }

    override suspend fun delete(movieId: Int) {
        dao.delete(movieId)
    }

    override fun getFavorites(): Flow<List<MovieResponse>> {
        return dao.getFavorites()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return dao.isFavorite(movieId)
    }
}