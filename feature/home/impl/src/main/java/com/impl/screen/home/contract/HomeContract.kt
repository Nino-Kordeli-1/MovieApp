package com.impl.screen.home.contract

import com.domain.model.GenreResponse
import com.domain.model.MovieResponse
import com.model.MovieUiModel
import kotlinx.coroutines.Job

data class HomeUiState(
    val genreList: List<GenreResponse> = emptyList(),
    val movieList: List<MovieUiModel> = emptyList(),
    var movies: List<MovieResponse> = emptyList(),
    val isLoading: Boolean = false,
    val hasMorePages: Boolean = true,
    val currentPage: Int = 1,
    val selectedGenre: Int? = null,
    var selectedGenreId: Int = 0,
    val error: String? = null,
    val searchQuery: String = "",
    var isConnected: Boolean = false,
    var lastMovieClickTime: Long = 0L,
    var searchJob: Job? = null,
    val isGenreListVisible: Boolean = false,
    val isSearchActive: Boolean = false,
    val favoriteId: Set<Int> = emptySet(),
    val scrollPosition: Int = 0,
    val noInternetPaging: Boolean = false
) {
    val isEmptyState get() = movieList.isEmpty() && !isLoading && searchQuery.isNotEmpty()
}

sealed interface HomeUiEvent {
    data class SearchChanged(val query: String) : HomeUiEvent
    data class GenreSelected(val genreId: Int) : HomeUiEvent
    data class FavoriteClicked(val movie: MovieUiModel) : HomeUiEvent
    data object RetryClicked : HomeUiEvent
    data class MovieClicked(val movieId: Int) : HomeUiEvent
    data object DeleteClicked : HomeUiEvent
    data object LoadNextPage : HomeUiEvent
    data object ToggleGenreFilter : HomeUiEvent
    data object SearchCancelled : HomeUiEvent
}

sealed interface HomeUiSideEffect {
    data class ShowError(val message: String) : HomeUiSideEffect
}