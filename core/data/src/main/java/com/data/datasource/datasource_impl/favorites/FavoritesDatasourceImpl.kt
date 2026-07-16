package com.data.datasource.datasource_impl.favorites

import com.data.datasource.FavoritesDatasource
import com.domain.model.MovieResponse
import com.movieapp.dao.FavoriteMovieDao
import com.movieapp.mapper.entity.FavoriteMovieEntityMapper
import com.movieapp.mapper.movie.FavoriteMovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesDatasourceImpl(
    private val dao: FavoriteMovieDao,
    private val favoriteMovieMapper: FavoriteMovieMapper,
    private val favoriteMovieEntityMapper: FavoriteMovieEntityMapper
) : FavoritesDatasource {
    override suspend fun insert(movie: MovieResponse) {
        dao.insert(
            favoriteMovieMapper.map(movie)
        )
    }

    override suspend fun delete(movieId: Int) {
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