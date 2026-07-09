package com.impl.screen.splash.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.resource.NetworkResult
import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val getGenres: GetGenresUseCase
) : ViewModel() {
    private val _movieResponse =
        MutableStateFlow<NetworkResult<List<MovieResponse>>>(NetworkResult.Loading)
    val movies = _movieResponse.asStateFlow()

    private val _genreResponse =
        MutableStateFlow<NetworkResult<List<GenreResponse>>>(NetworkResult.Loading)
    val genre = _genreResponse.asStateFlow()

    init {
        fetchMovies()
        fetchGenres()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            getPopularMovies(page = 1).collect {
                _movieResponse.value = it
            }
        }
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            getGenres().collect {
                _genreResponse.value = it
            }
        }
    }
}