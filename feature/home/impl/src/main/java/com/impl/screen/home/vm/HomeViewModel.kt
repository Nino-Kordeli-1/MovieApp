package com.impl.screen.home.vm

import androidx.lifecycle.viewModelScope
import com.api.navigation.DetailsNavKey
import com.common.resource.NetworkResult
import com.domain.model.MovieResponse
import com.domain.observer.ConnectivityObserver
import com.domain.usecase.AddFavoriteUseCase
import com.domain.usecase.DiscoverByGenreUseCase
import com.domain.usecase.GetFavoriteUseCase
import com.domain.usecase.GetGenresUseCase
import com.domain.usecase.GetPopularMoviesUseCase
import com.domain.usecase.RemoveFavoriteUseCase
import com.domain.usecase.SearchMoviesUseCase
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.contract.HomeUiSideEffect
import com.impl.screen.home.contract.HomeUiSideEffect.ShowError
import com.impl.screen.home.contract.HomeUiState
import com.impl.screen.home.mapper.MovieUiMapper
import com.impl.screen.home.mapper.MovieUiMapperInput
import com.model.MovieUiModel
import com.navigation.NavCommand
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val discoverByGenreUseCase: DiscoverByGenreUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val connectivityObserver: ConnectivityObserver,
    private val movieUiMapper: MovieUiMapper
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiSideEffect>(
    HomeUiState()
) {
    private var selectedGenreId = 0
    private var searchJob: Job? = null
    private var movies: List<MovieResponse> = emptyList()
    private var lastMovieClickTime = 0L
    private var isConnectedState = false

    init {
        observeNetwork()
        observeFavorites()
        observeGenres()
        loadData()
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.FavoriteClicked -> {
                onFavoriteClick(event.movie)
            }

            is HomeUiEvent.GenreSelected -> {
                onGenreSelected(event.genreId)
            }

            is HomeUiEvent.MovieClicked -> {
                onMovieClick(event.movieId)
            }

            is HomeUiEvent.SearchChanged -> {
                onSearchChanged(event.query)
            }

            HomeUiEvent.LoadNextPage -> {
                loadNextPage()
            }

            HomeUiEvent.ToggleGenreFilter -> {
                toggleGenreFilter()
            }

            HomeUiEvent.SearchCancelled -> {
                onSearchCanceled()
            }

            HomeUiEvent.RetryClicked -> {
                retryClicked()
            }

            HomeUiEvent.DeleteClicked -> {
                onDeleteClicked()
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
                        handleError(result.errorMessage)
                    }

                    NetworkResult.Loading -> {
                        setLoading()
                    }

                    is NetworkResult.Success -> {
                        val filteredMovies = result.data.filter {
                            it.title.contains(
                                query,
                                ignoreCase = true
                            )
                        }

                        updateMovies(
                            newMovies = filteredMovies,
                            page = page
                        )
                    }
                }
            }
        }
    }

    private fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            searchMoviesUseCase(query = query, page = page).collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        handleError(result.errorMessage)
                    }

                    NetworkResult.Loading -> {
                        setLoading()
                    }

                    is NetworkResult.Success -> {
                        updateMovies(result.data, page)
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
                        handleError(result.errorMessage)
                    }

                    NetworkResult.Loading -> {
                        setLoading()
                    }

                    is NetworkResult.Success -> {
                        updateMovies(result.data, page)
                    }
                }
            }
        }
    }

    private fun getPopularMovies(page: Int) {
        collectMovies(
            flow = getPopularMoviesUseCase(page),
            page = page
        )
    }

    private fun observeGenres() {
        viewModelScope.launch {
            getGenresUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        handleError(result.errorMessage)
                    }

                    NetworkResult.Loading -> {
                        setLoading()
                    }

                    is NetworkResult.Success -> {
                        updateState {
                            it.copy(
                                genreList = result.data,
                                movieList = movieUiMapper.map(
                                    MovieUiMapperInput(
                                        movies = movies,
                                        genres = result.data,
                                        favoriteIds = state.value.favoriteId
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteUseCase().collect { favorites ->
                val ids = favorites.map {
                    it.id
                }.toSet()

                updateState {

                    it.copy(
                        favoriteId = ids,
                        movieList = movieUiMapper.map(
                            MovieUiMapperInput(
                                movies = movies,
                                genres = state.value.genreList,
                                favoriteIds = ids
                            )
                        )
                    )
                }
            }
        }
    }

    private fun updateMovies(
        newMovies: List<MovieResponse>,
        page: Int
    ) {
        if (page == 1) {
            movies = newMovies
        } else {
            movies += newMovies
        }

        updateState {
            it.copy(
                isLoading = false,
                hasMorePages = newMovies.isNotEmpty(),
                movieList = movieUiMapper.map(
                    MovieUiMapperInput(
                        movies = movies,
                        genres = it.genreList,
                        favoriteIds = it.favoriteId
                    )
                )
            )
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { connected ->
                isConnectedState = connected
                if (!connected) {
                    updateState { it.copy(isConnected = false) }
                }
            }
        }
    }

    private fun setLoading() {
        updateState {
            it.copy(isLoading = true)
        }
    }

    private fun handleError(message: String) {
        updateState {
            it.copy(
                isLoading = false,
                error = message
            )
        }
        emitSideEffect(ShowError(message))
    }

    private fun collectMovies(
        flow: Flow<NetworkResult<List<MovieResponse>>>,
        page: Int,
        query: String? = null
    ) {
        viewModelScope.launch {
            flow.collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        handleError(result.errorMessage)
                    }

                    NetworkResult.Loading -> {
                        setLoading()
                    }

                    is NetworkResult.Success -> {
                        val moviesToDisplay =
                            if (query == null) {
                                result.data
                            } else {
                                result.data.filter {
                                    it.title.contains(
                                        query,
                                        ignoreCase = true
                                    )
                                }
                            }

                        updateMovies(
                            newMovies = moviesToDisplay,
                            page = page
                        )
                    }
                }
            }
        }
    }

    private fun loadData() {
        updateState {
            it.copy(
                isLoading = true,
                movieList = emptyList(),
                genreList = emptyList()
            )
        }
        movies = emptyList()

        updateState {
            it.copy(
                currentPage = 1,
                error = null
            )
        }

        when {
            state.value.searchQuery.isNotBlank() && selectedGenreId != 0 -> {
                searchMoviesInGenre(
                    query = state.value.searchQuery,
                    genreId = selectedGenreId,
                    page = 1
                )
            }

            state.value.searchQuery.isNotBlank() -> {
                searchMovies(
                    query = state.value.searchQuery,
                    page = 1
                )
            }

            selectedGenreId != 0 -> {
                discoverByGenre(
                    genreId = selectedGenreId,
                    page = 1
                )
            }

            else -> {
                getPopularMovies(page = 1)
            }
        }
    }

    fun onMovieClick(movieId: Int) {
        val now = System.currentTimeMillis()
        if (now - lastMovieClickTime < 500) return
        lastMovieClickTime = now
        navigate(NavCommand.Navigate(DetailsNavKey(movieId)))
    }

    private fun onFavoriteClick(movie: MovieUiModel) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                removeFavoriteUseCase(
                    movie.id
                )
            } else {
                val movieToAdd = movies.firstOrNull { it.id == movie.id } ?: return@launch

                addFavoriteUseCase(movieToAdd)
            }
        }
    }

    private fun onGenreSelected(genreId: Int) {
        if (selectedGenreId == genreId) {
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
            selectedGenreId = genreId
            movies = emptyList()

            updateState {
                it.copy(
                    selectedGenre = genreId,
                    currentPage = 1
                )
            }
            discoverByGenre(
                genreId = genreId,
                page = 1
            )
        }
    }

    private fun onSearchChanged(query: String) {
        val isActive = query.isNotEmpty()
        updateState {
            it.copy(
                searchQuery = query,
                currentPage = 1,
                hasMorePages = true,
                isSearchActive = isActive,
                isGenreListVisible = if (isActive) false else it.isGenreListVisible
            )
        }
        searchJob?.cancel()

        if (query.isEmpty()) {
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
                        query = query,
                        page = 1
                    )
                } else {
                    searchMoviesInGenre(
                        query = query,
                        page = 1,
                        genreId = selectedGenreId
                    )
                }
            }
        }
    }

    private fun loadNextPage() {
        val currentState = state.value
        if (currentState.isLoading || !currentState.hasMorePages) return
        val nextPage = currentState.currentPage + 1
        updateState {
            it.copy(currentPage = nextPage)
        }
        when {
            currentState.searchQuery.isNotBlank() && selectedGenreId != 0 -> {
                searchMoviesInGenre(
                    query = currentState.searchQuery,
                    genreId = selectedGenreId,
                    page = nextPage
                )
            }

            currentState.searchQuery.isNotBlank() -> {
                searchMovies(
                    query = currentState.searchQuery,
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

    private fun toggleGenreFilter() {
        updateState {
            it.copy(
                isGenreListVisible = !it.isGenreListVisible
            )
        }
    }

    private fun onDeleteClicked() {
        searchJob?.cancel()

        val currentQuery = state.value.searchQuery
        if (currentQuery.isNotEmpty()) {
            val newQuery = currentQuery.dropLast(1)

            updateState {
                it.copy(
                    searchQuery = newQuery,
                    currentPage = 1,
                    isSearchActive = true
                )
            }
        }
    }

    private fun onSearchCanceled() {
        searchJob?.cancel()
        movies = emptyList()

        updateState {
            it.copy(
                searchQuery = "",
                currentPage = 1,
                isSearchActive = false
            )
        }
        if (selectedGenreId != 0) {
            discoverByGenre(
                genreId = selectedGenreId,
                page = 1
            )
        } else
            getPopularMovies(page = 1)
    }

    private fun retryClicked() {
        if (isConnectedState) {
            updateState { it.copy(isConnected = true) }
            observeFavorites()
            observeGenres()
            loadData()
        }
    }
}