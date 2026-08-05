package com.impl.screen.favorites.contract

import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.model.MovieUiModel

data class FavoritesUiState(
    val favorites: List<MovieUiModel> = emptyList(),
    val genreList: List<GenreResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    var movies: List<MovieResponse> = emptyList()
)

sealed interface FavoritesUiEvent {
    data class RemoveFavorite(val movieId: Int) : FavoritesUiEvent
    data class MovieClicked(val movieId: Int) : FavoritesUiEvent
}