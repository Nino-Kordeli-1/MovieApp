package com.data.datasource.datasource_impl.search_and_genre

import com.data.datasource.SearchAndGenreDatasource
import com.data.dto.genre.GenreListResponseDto
import com.data.dto.movie.MovieResponseDto
import com.data.remote.SearchAndGenreApi
import retrofit2.Response

class SearchAndGenreDatasourceImpl(
    private val api: SearchAndGenreApi
) : SearchAndGenreDatasource {
    override suspend fun getGenres():
            Response<GenreListResponseDto> {
        return api.getGenres()
    }

    override suspend fun discoverByGenre(
        genreId: Int,
        page: Int
    ): Response<MovieResponseDto> {
        return api.discoverByGenre(genreId = genreId, page = page)
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Response<MovieResponseDto> {
        return api.searchMovies(query = query, page = page)
    }
}