package com.data.repository

import com.network.mapper.asResource
import com.common.resource.NetworkResult
import com.data.datasource.MovieDatasource
import com.data.mapper.MovieMapper
import com.domain.model.MovieResponse
import com.domain.repository.MovieRepository
import com.network.response_handler.apiCall
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val dataSource: MovieDatasource,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override fun getMovies(page: Int): Flow<NetworkResult<List<MovieResponse>>> {
        return apiCall { dataSource.getPopularMovies(page) }
            .asResource { dto ->
                dto.results.map(movieMapper::map)
            }
    }
}