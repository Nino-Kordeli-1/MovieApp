package com.impl.screen.details.contract

import com.model.MovieDetailsUiModel

data class DetailsUiState(
    val movie: MovieDetailsUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface DetailsUiEvent {
    data object FavoriteClicked : DetailsUiEvent
    data object BackClicked : DetailsUiEvent
}