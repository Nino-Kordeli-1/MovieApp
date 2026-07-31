package com.impl.screen.details.vm

import androidx.lifecycle.viewModelScope
import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.usecase.AddFavoriteUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetMovieByIdUseCase
import com.domain.usecase.IsFavoriteUseCase
import com.domain.usecase.RemoveFavoriteUseCase
import com.impl.screen.details.contract.DetailsUiEvent
import com.impl.screen.details.contract.DetailsUiState
import com.impl.screen.details.mapper.MovieDetailsUiMapper
import com.impl.screen.details.mapper.MovieDetailsUiMapperInput
import com.navigation.NavCommand
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieId: Int,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val movieDetailsUiMapper: MovieDetailsUiMapper
) : BaseViewModel<DetailsUiState, DetailsUiEvent, Nothing>(DetailsUiState()) {

    init {
        loadData()
    }

    override fun onEvent(event: DetailsUiEvent) {
        when (event) {
            DetailsUiEvent.BackClicked -> {
                onBackClick()
            }

            DetailsUiEvent.FavoriteClicked -> {
                toggleFavorite()
            }
        }
    }

    private fun loadMovie() {
        viewModelScope.launch {
            getMovieByIdUseCase(movieId).collect { result ->
                when (result) {
                    NetworkResult.Loading -> updateState { it.copy(isLoading = true) }
                    is NetworkResult.Error -> updateState {
                        it.copy(isLoading = false, error = result.errorMessage)
                    }

                    is NetworkResult.Success -> {
                        state.value.currentMovie = result.data

                        val isFavorite = isFavoriteUseCase(movieId).first()
                        val movieUi = movieDetailsUiMapper.map(
                            MovieDetailsUiMapperInput(
                                movie = result.data,
                                isFavorite = isFavorite
                            )
                        )

                        updateState {
                            it.copy(
                                isLoading = false,
                                movie = movieUi
                            )
                        }
                        observeFavoriteStatus()
                    }
                }
            }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            isFavoriteUseCase(movieId).collect { isFavorite ->
                updateState {
                    it.copy(movie = it.movie?.copy(isFavorite = isFavorite))
                }
            }
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val movie = state.value.currentMovie ?: return@launch
            val isFavorite = isFavoriteUseCase(movieId).first()
            if (isFavorite) {
                removeFavoriteUseCase(movieId)
            } else {
                with(movie) {
                    addFavoriteUseCase(
                        MovieResponse(
                            id = id,
                            popularity = 0.0,
                            backdropPath = backdropPath,
                            genreIds = genres.map { it.id },
                            originalTitle = originalTitle,
                            overview = overview,
                            posterPath = posterPath,
                            releaseDate = releaseDate,
                            title = title,
                            voteAverage = voteAverage,
                            runtime = runtime
                        )
                    )
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {

            getGenresUseCase().collect { result ->

                when (result) {

                    NetworkResult.Loading -> {
                        updateState { it.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        result.errorMessage
                    }

                    is NetworkResult.Success -> {
                        if (result.data.isEmpty()) return@collect
                        state.value.genres = result.data
                        loadMovie()
                        return@collect
                    }
                }
            }
        }
    }

    private fun onBackClick() {
        navigate(NavCommand.Back)
    }
}
