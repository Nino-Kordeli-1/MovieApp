package com.impl.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.common.resource.NetworkResult
import com.domain.usecase.DiscoverByGenreUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import com.domain.usecase.SearchMoviesUseCase
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.contract.HomeUiSideEffect
import com.impl.screen.home.contract.HomeUiSideEffect.*
import com.impl.screen.home.contract.HomeUiState
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val discoverByGenreUseCase: DiscoverByGenreUseCase,
    private var searchJob: Job? = null
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(
    HomeUiState()
) {
    private var selectedGenreId = 0

    init {
//        observeGenres()
        getPopularMovies(page = 1)
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.FavoriteClicked -> {

            }

            is HomeUiEvent.GenreSelected -> {
                selectedGenreId = event.genreId
                updateState {
                    it.copy(
                        currentPage = 1
                    )
                }
            }

            is HomeUiEvent.MovieClicked -> {
                emitSideEffect(
                    (NavigateToDetails(event.movieId))
                )
            }

            is HomeUiEvent.SearchChanged -> {
                updateState {
                    it.copy(
                        searchQuery = event.query,
                        currentPage = 1
                    )
                }//TODO take me to the top after search

                if (event.query.isEmpty()) {
                    getPopularMovies(page = state.value.currentPage)
                } else {
                    searchJob?.cancel()

                    searchJob = viewModelScope.launch {
                        delay(300.milliseconds)
                        searchMovies(
                            query = event.query,
                            page = state.value.currentPage
                        )
                    }
                }
            }

            HomeUiEvent.LoadNextPage -> {
                if (state.value.isLoading || !state.value.hasMorePages) return
                updateState {
                    it.copy(currentPage = state.value.currentPage + 1)
                }
                getPopularMovies(page = state.value.currentPage + 1)
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
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = result.data
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
                    is NetworkResult.Error -> {}
                    NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {}
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
                        updateState {
                            it.copy(
                                error = null,
                                isLoading = false,
                                movieList = if (state.value.currentPage == 1) {
                                    result.data
                                } else {
                                    state.value.movieList + result.data
                                }
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

                    NetworkResult.Loading -> {}

                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(genreList = result.data)
                        }
                    }
                }
            }
        }
    }

    /*private fun discoverMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            discoverByGenre(
                genreId = genreId,
                page = 1
            ).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = result.errorMessage
                            )
                        }
                        emitSideEffect(HomeSideEffect.ShowError(result.errorMessage))
                    }

                    NetworkResult.Loading -> {
                        updateState {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = result.data,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }*/

    /*private fun searchMovies(query: String) {
        viewModelScope.launch {
            search(
                query = query,
                page = 1
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
                        updateState { it.copy(isLoading = true) }
                    }

                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                movieList = result.data,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }*/
}

