package com.impl.screen.favorites.contract

import com.model.MovieUiModel
import com.domain.model.GenreResponse

data class FavoritesUiState(
    val favorites: List<MovieUiModel> = emptyList(),
    val genreList: List<GenreResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface FavoritesUiEvent {
    data class RemoveFavorite(val movieId: Int) : FavoritesUiEvent
    data class MovieClicked(val movieId: Int) : FavoritesUiEvent
}

sealed interface FavoritesUiSideEffect {
    data class NavigateToDetails(val movieId: Int) : FavoritesUiSideEffect
}