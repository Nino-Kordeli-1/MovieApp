package com.impl.screen.favorites.vm

import androidx.lifecycle.viewModelScope
import com.api.navigation.DetailsNavKey
import com.common.resource.NetworkResult
import com.domain.usecase.GetFavoriteUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.RemoveFavoriteUseCase
import com.impl.screen.favorites.contract.FavoritesUiEvent
import com.impl.screen.favorites.contract.FavoritesUiState
import com.impl.screen.favorites.mapper.MovieUiMapper
import com.impl.screen.favorites.mapper.MovieUiMapperInput
import com.navigation.NavCommand
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val movieUiMapper: MovieUiMapper
) : BaseViewModel<FavoritesUiState, FavoritesUiEvent, Nothing>(
    FavoritesUiState()
) {

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
                navigate(NavCommand.Navigate(DetailsNavKey(event.movieId)))
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase().collect { favorites ->
                state.value.movies = favorites
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
                favorites = movieUiMapper.map(
                    MovieUiMapperInput(
                        movies = state.value.movies,
                        genres = it.genreList,
                        favoriteIds = state.value.movies.map { movie -> movie.id }.toSet()
                    )
                )
            )
        }
    }
}