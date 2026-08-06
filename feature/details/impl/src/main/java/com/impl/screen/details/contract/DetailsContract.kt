package com.impl.screen.details.contract

import com.domain.model.GenreResponse
import com.domain.model.MovieDetails
import com.model.MovieDetailsUiModel

data class DetailsUiState(
    val movie: MovieDetailsUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    var genres: List<GenreResponse> = emptyList(),
    var currentMovie: MovieDetails? = null
)

sealed interface DetailsUiEvent {
    data object FavoriteClicked : DetailsUiEvent
    data object BackClicked : DetailsUiEvent
}