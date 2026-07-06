package com.impl.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.usecase.DiscoverByGenreUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import com.domain.usecase.SearchMoviesUseCase
import com.impl.mapper.movieUiMapper
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.contract.HomeUiSideEffect
import com.impl.screen.home.contract.HomeUiSideEffect.NavigateToDetails
import com.impl.screen.home.contract.HomeUiSideEffect.ShowError
import com.impl.screen.home.contract.HomeUiState
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val discoverByGenreUseCase: DiscoverByGenreUseCase,
    private var searchJob: Job? = null,
    private var movies: List<MovieResponse> = emptyList()
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(
    HomeUiState()
) {
    private var selectedGenreId = 0

    init {
        observeGenres()
        getPopularMovies(page = 1)
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.FavoriteClicked -> {}

            is HomeUiEvent.GenreSelected -> {
                if (selectedGenreId == event.genreId) {
                    selectedGenreId = 0
                    movies = emptyList()

                    updateState {
                        it.copy(
                            selectedGenre = null,
                            currentPage = 1
                        )
                    }

                    getPopularMovies(page = 1)

                } else {
                    selectedGenreId = event.genreId
                    movies = emptyList()

                    updateState {
                        it.copy(
                            currentPage = 1,
                            selectedGenre = event.genreId
                        )
                    }
                    discoverByGenre(
                        genreId = event.genreId,
                        page = 1
                    )
                }
            }

            is HomeUiEvent.MovieClicked -> {
                emitSideEffect(
                    (NavigateToDetails(event.movieId))
                )
            }

            is HomeUiEvent.SearchChanged -> {
                val isActive = event.query.isNotEmpty()

                updateState {
                    it.copy(
                        searchQuery = event.query,
                        currentPage = 1,
                        isSearchActive = isActive,
                        isGenreListVisible = if (isActive) false else it.isGenreListVisible
                    )
                }

                searchJob?.cancel()

                if (event.query.isEmpty()) {
                    movies = emptyList()
                    if (selectedGenreId != 0) {
                        discoverByGenre(
                            page = 1,
                            genreId = selectedGenreId
                        )
                    } else {
                        getPopularMovies(page = 1)
                    }
                } else {
                    searchJob = viewModelScope.launch {
                        delay(300.milliseconds)
                        if (selectedGenreId == 0) {
                            searchMovies(
                                query = event.query,
                                page = 1
                            )
                        } else {
                            searchMoviesInGenre(
                                query = event.query,
                                page = 1,
                                genreId = selectedGenreId
                            )
                        }
                    }
                }
            }

            HomeUiEvent.LoadNextPage -> {
                if (state.value.isLoading || !state.value.hasMorePages) return
                val nextPage = state.value.currentPage + 1
                updateState {
                    it.copy(currentPage = state.value.currentPage + 1)
                }
                when {
                    state.value.searchQuery.isNotBlank() && selectedGenreId != 0 -> {
                        searchMoviesInGenre(
                            query = state.value.searchQuery,
                            genreId = selectedGenreId,
                            page = nextPage
                        )
                    }

                    state.value.searchQuery.isNotBlank() -> {
                        searchMovies(
                            query = state.value.searchQuery,
                            page = nextPage,
                        )
                    }

                    selectedGenreId != 0 -> {
                        discoverByGenre(
                            genreId = selectedGenreId,
                            page = nextPage
                        )
                    }

                    else -> getPopularMovies(page = nextPage)
                }
            }

            HomeUiEvent.ToggleGenreFilter -> {
                updateState {
                    it.copy(
                        isGenreListVisible = !it.isGenreListVisible
                    )
                }
            }

            HomeUiEvent.SearchCancelled -> {
                searchJob?.cancel()

                movies = emptyList()

                updateState {
                    it.copy(
                        searchQuery = "",
                        isSearchActive = false,
                        currentPage = 1
                    )
                }
                if (selectedGenreId != 0) {
                    discoverByGenre(
                        genreId = selectedGenreId,
                        page = 1
                    )
                } else {
                    getPopularMovies(page = 1)
                }
            }
        }
    }

    private fun searchMoviesInGenre(
        query: String,
        genreId: Int,
        page: Int
    ) {
        viewModelScope.launch {
            discoverByGenreUseCase(
                genreId = genreId,
                page = page
            ).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }
                    }

                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        val filteredMovieList =
                            result.data.filter {
                                it.title.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }

                        if (page == 1) {
                            movies = filteredMovieList
                        } else {
                            movies += filteredMovieList
                        }
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = movieUiMapper(
                                    movies = movies,
                                    genres = state.value.genreList
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            searchMoviesUseCase(query = query, page = page).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {}
                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        if (page == 1) {
                            movies = result.data
                        } else {
                            movies += result.data
                        }
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = movieUiMapper(
                                    movies = movies,
                                    genres = state.value.genreList
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun discoverByGenre(genreId: Int, page: Int) {
        viewModelScope.launch {
            discoverByGenreUseCase(genreId = genreId, page = page).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }
                    }

                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        if (page == 1) {
                            movies = result.data
                        } else {
                            movies += result.data
                        }
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = movieUiMapper(
                                    movies = movies,
                                    genres = state.value.genreList
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            getPopularMoviesUseCase(page = page).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        updateState {
                            it.copy(
                                error = result.errorMessage,
                                isLoading = false
                            )
                        }
                        emitSideEffect(
                            ShowError(result.errorMessage)
                        )
                    }

                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        if (page == 1) {
                            movies = result.data
                        } else {
                            movies += result.data
                        }
                        updateState {
                            it.copy(
                                error = null,
                                isLoading = false,
                                movieList = movieUiMapper(
                                    movies = movies,
                                    genres = state.value.genreList
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        emitSideEffect(
                            ShowError(result.errorMessage)
                        )
                    }

                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(isLoading = true)
                        }
                    }

                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(
                                genreList = result.data,
                                movieList = movieUiMapper(
                                    movies = movies,
                                    genres = result.data
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}