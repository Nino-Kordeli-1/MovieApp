package com.data.repository

import com.network.mapper.asResource
import com.common.resource.NetworkResult
import com.data.datasource.SearchAndGenreDatasource
import com.data.mapper.GenreMapper
import com.domain.model.GenreResponse
import com.domain.repository.GenreRepository
import com.network.response_handler.apiCall
import kotlinx.coroutines.flow.Flow

class GenreRepositoryImpl(
    private val dataSource: SearchAndGenreDatasource,
    private val genreMapper: GenreMapper
) : GenreRepository {
    override fun getGenres(
    ): Flow<NetworkResult<List<GenreResponse>>> {
        return apiCall {
            dataSource.getGenres()
        }.asResource { dto ->
            dto.genres.map(genreMapper::map)
        }
    }
}