package com.impl.screen.favorites.vm

import androidx.lifecycle.viewModelScope
import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.usecase.GetFavoriteUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.RemoveFavoriteUseCase
import com.impl.screen.favorites.contract.FavoritesUiEvent
import com.impl.screen.favorites.contract.FavoritesUiSideEffect
import com.impl.screen.favorites.contract.FavoritesUiState
import com.ui.base.vm.BaseViewModel
import com.ui.mapper.movieUiMapper
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : BaseViewModel<FavoritesUiState, FavoritesUiEvent, FavoritesUiSideEffect>(
    FavoritesUiState()
) {
    private var movies: List<MovieResponse> = emptyList()

    init {
        observeGenres()
        observeFavorites()
    }

    override fun onEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    removeFavoriteUseCase(event.movieId)
                }
            }
            is FavoritesUiEvent.MovieClicked -> {
                emitSideEffect(FavoritesUiSideEffect.NavigateToDetails(event.movieId))
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase().collect { favorites ->
                movies = favorites
                updateFavoritesList()
            }
        }
    }

    private fun observeGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                when (result) {
                    NetworkResult.Loading -> updateState { it.copy(isLoading = true) }
                    is NetworkResult.Error -> updateState {
                        it.copy(isLoading = false, error = result.errorMessage)
                    }
                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                genreList = result.data
                            )
                        }
                        updateFavoritesList()
                    }
                }
            }
        }
    }

    private fun updateFavoritesList() {
        updateState {
            it.copy(
                favorites = movieUiMapper(
                    movies = movies,
                    genres = it.genreList,
                    favoriteIds = movies.map { movie -> movie.id }.toSet()
                )
            )
        }
    }
}