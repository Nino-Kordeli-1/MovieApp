package com.impl.screen.favorites.contract

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.model.MovieUiModel
import com.domain.model.GenreResponse
import com.domain.model.MovieResponse

data class FavoritesUiState(
    val favorites: List<MovieUiModel> = emptyList(),
    val genreList: List<GenreResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    var movies: List<MovieResponse> = emptyList()
) {
    var gridState: LazyGridState by mutableStateOf(LazyGridState(0, 0))
        private set
}

sealed interface FavoritesUiEvent {
    data class RemoveFavorite(val movieId: Int) : FavoritesUiEvent
    data class MovieClicked(val movieId: Int) : FavoritesUiEvent
}