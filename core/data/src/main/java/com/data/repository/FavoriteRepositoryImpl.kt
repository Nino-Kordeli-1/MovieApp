package com.data.repository

import com.domain.model.MovieResponse
import com.domain.repository.FavoriteRepository
import com.movieapp.dao.FavoriteMovieDao
import com.movieapp.mapper.entity.FavoriteMovieEntityMapper
import com.movieapp.mapper.movie.FavoriteMovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val dao: FavoriteMovieDao,
    private val favoriteMovieMapper: FavoriteMovieMapper,
    private val favoriteMovieEntityMapper: FavoriteMovieEntityMapper
) : FavoriteRepository {
    override suspend fun addFavorite(movie: MovieResponse) {
        dao.insert(
            favoriteMovieMapper.map(movie)
        )
    }

    override suspend fun removeFavorite(movieId: Int) {
        dao.delete(movieId)
    }

    override fun getFavorites(): Flow<List<MovieResponse>> {
        return dao.getFavorites()
            .map { entities ->
                entities.map { entity ->
                    favoriteMovieEntityMapper.map(entity)
                }
            }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return dao.isFavorite(movieId)
    }
}