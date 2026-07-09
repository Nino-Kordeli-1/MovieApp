package com.data.repository

import com.common.mapper.asResource
import com.common.resource.NetworkResult
import com.data.datasource.SearchAndGenreDatasource
import com.data.mapper.MovieMapper
import com.domain.model.MovieResponse
import com.domain.repository.SearchAndGenreRepository
import com.network.response_handler.apiCall
import kotlinx.coroutines.flow.Flow

class SearchAndGenreRepositoryImpl(
    private val dataSource: SearchAndGenreDatasource,
    private val movieMapper: MovieMapper
) : SearchAndGenreRepository {
    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Flow<NetworkResult<List<MovieResponse>>> {
        return apiCall {
            dataSource.searchMovies(
                query = query,
                page = page
            )
        }.asResource { dto ->
            dto.results.map(movieMapper::map)
        }
    }

    override suspend fun discoverByGenre(
        genreId: Int,
        page: Int
    ): Flow<NetworkResult<List<MovieResponse>>> {
        return apiCall {
            dataSource.discoverByGenre(
                genreId = genreId,
                page = page
            )
        }.asResource { dto ->
            dto.results.map(movieMapper::map)
        }
    }
}