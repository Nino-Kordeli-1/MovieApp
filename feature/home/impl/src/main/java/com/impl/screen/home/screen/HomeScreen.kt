package com.impl.screen.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.Spacing
import com.designsystem.theme.MovieAppTheme
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral08Whisper
import com.impl.screen.home.components.CustomPaging
import com.impl.screen.home.components.FilterButton
import com.impl.screen.home.components.GenreListLabel
import com.impl.screen.home.components.SearchBar
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.contract.HomeUiState
import com.impl.screen.home.vm.HomeViewModel
import com.model.MovieUiModel
import com.movieapp.impl.home.R
import com.ui.components.empty_state.EmptyScreen
import com.ui.components.loader.MovieLoadingIndicator
import com.ui.components.movie_grid.MovieGrid

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {

    var headerVisible by remember { mutableStateOf(true) }
    val gridState = rememberLazyGridState()
    var lastIndex by remember { mutableIntStateOf(gridState.firstVisibleItemIndex) }

    LaunchedEffect(state.scrollToTopTrigger) {
        val current = gridState.firstVisibleItemIndex
        if (state.scrollToTopTrigger > 0) {
            gridState.scrollToItem(0)
        }

        headerVisible = when {
            current == 0 -> true
            current > lastIndex -> false
            current < lastIndex -> true
            else -> headerVisible
        }

        lastIndex = current
    }

    if (state.isConnected == false && state.movieList.isEmpty()) {
        NoInternetScreen(
            onRetry = { onEvent(HomeUiEvent.RetryClicked) }
        )
        return
    }

    if (state.isLoading && state.movieList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Neutral01Black),
            contentAlignment = Alignment.Center
        ) {
            MovieLoadingIndicator()
        }
        return
    }

    CustomPaging(
        gridState = gridState,
        isLoading = state.isLoading,
        hasMorePages = state.hasMorePages,
        itemCount = state.movieList.size,
        onLoadMore = {
            onEvent(HomeUiEvent.LoadNextPage)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Neutral01Black)
    ) {
        AnimatedVisibility(
            visible = headerVisible,
            enter = fadeIn(tween(150)) + slideInVertically { -it } + expandVertically(tween(150)),
            exit = fadeOut(tween(150)) + slideOutVertically { -it } + shrinkVertically(tween(150))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Spacing.spacing_44,
                        start = Spacing.spacing_12,
                        end = Spacing.spacing_12
                    )
                    .background(
                        color = Neutral01Black.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(Spacing.spacing_16)
                    )
                    .shadow(
                        elevation = Spacing.spacing_56,
                        shape = RoundedCornerShape(Spacing.spacing_16),
                        clip = false
                    )
                    .clip(RoundedCornerShape(Spacing.spacing_16))
                    .padding(Spacing.spacing_12)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchBar(
                            query = state.searchQuery,
                            onQueryChange = { onEvent(HomeUiEvent.SearchChanged(it)) },
                            modifier = Modifier.weight(1f),
                            onDeleteClick = { onEvent(HomeUiEvent.DeleteClicked) }
                        )

                        Spacer(Modifier.width(Spacing.spacing_8))

                        if (state.isSearchActive) {
                            Text(
                                text = stringResource(R.string.feature_home_impl_cancel),
                                color = Neutral08Whisper,
                                modifier = Modifier.clickable { onEvent(HomeUiEvent.SearchCancelled) }
                            )
                        } else {
                            FilterButton(
                                isSelected = state.isGenreListVisible,
                                onClick = { onEvent(HomeUiEvent.ToggleGenreFilter) }
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = state.isGenreListVisible && !state.isSearchActive
                    ) {
                        LazyRow(
                            modifier = Modifier.padding(top = Spacing.spacing_12)
                        ) {
                            items(state.genreList) { genre ->
                                GenreListLabel(
                                    isSelected = genre.id == state.selectedGenre,
                                    onClick = { onEvent(HomeUiEvent.GenreSelected(genre.id)) },
                                    title = genre.name
                                )
                            }
                        }
                    }

                    if (state.isEmptyState) {
                        EmptyScreen(text = "No results found")
                    }
                }
            }
        }

        MovieGrid(
            title = "Movies",
            isLoading = false,
            isOffline = state.isConnected == false && state.movieList.isNotEmpty(),
            gridState = gridState,
            movies = state.movieList,
            onMovieClick = { movieId ->
                onEvent(HomeUiEvent.MovieClicked(movieId))
            },
            onFavoriteClick = { movie ->
                onEvent(HomeUiEvent.FavoriteClicked(movie))
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreenContent(
            state = HomeUiState(
                isConnected = true,
                isLoading = false,
                movieList = listOf(
                    MovieUiModel(
                        id = 1,
                        title = "Sample Movie",
                        posterPath = "",
                        releaseDate = "2024",
                        genre = "Action",
                        isFavorite = false,
                        popularity = 5.0,
                        backdropPath = "",
                        originalTitle = "Movie",
                        overview = "",
                        voteAverage = 6.0
                    )
                ),
                selectedGenre = 1,
                searchQuery = "",
                isSearchActive = false,
                isGenreListVisible = true,
                hasMorePages = true
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun HomeScreenNoInternetPreview() {
    MovieAppTheme {
        HomeScreenContent(
            state = HomeUiState(isConnected = false),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun HomeScreenEmptyPreview() {
    MovieAppTheme {
        HomeScreenContent(
            state = HomeUiState(
                isConnected = true,
                isLoading = false,
                movieList = emptyList(),
                searchQuery = "asdkfjhaskdf"
            ),
            onEvent = {},
        )
    }
}