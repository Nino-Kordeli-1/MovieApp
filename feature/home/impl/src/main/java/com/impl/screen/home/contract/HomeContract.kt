package com.impl.screen.home.contract

import com.domain.model.GenreResponse
import com.impl.screen.home.model.MovieUiModel

data class HomeUiState(
    val genreList: List<GenreResponse> = emptyList(),
    val movieList: List<MovieUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val hasMorePages: Boolean = true,
    val currentPage: Int = 1,
    val selectedGenre: Int? = null,
    val error: String? = null,
    val searchQuery: String = "",
    val isGenreListVisible: Boolean = false,
    val isSearchActive: Boolean = false
)

sealed interface HomeUiEvent {
    data class SearchChanged(val query: String) : HomeUiEvent
    data class GenreSelected(val genreId: Int) : HomeUiEvent
    data class FavoriteClicked(val movieId: Int) : HomeUiEvent
    data class MovieClicked(val movieId: Int) : HomeUiEvent
    data object LoadNextPage : HomeUiEvent
    data object ToggleGenreFilter : HomeUiEvent
    data object SearchCancelled : HomeUiEvent
}

sealed interface HomeUiSideEffect {
    data class ShowError(val message: String) : HomeUiSideEffect
    data class NavigateToDetails(val movieId: Int) : HomeUiSideEffect
}