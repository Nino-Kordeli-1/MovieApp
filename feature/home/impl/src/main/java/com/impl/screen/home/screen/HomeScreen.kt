package com.impl.screen.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary
import com.impl.screen.home.components.FilterButton
import com.impl.screen.home.components.GenreListLabel
import com.impl.screen.home.components.SearchBar
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.vm.HomeViewModel
import com.ui.components.movie_card.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    LaunchedEffect(state.searchQuery) {
        gridState.scrollToItem(0)
    }

    LaunchedEffect(state.selectedGenre) {
        gridState.scrollToItem(0)
    }

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            val total = gridState.layoutInfo.totalItemsCount
            lastVisible.index >= total - 2
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && state.hasMorePages) {
            viewModel.onEvent(HomeUiEvent.LoadNextPage)
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()//TODO ERROR INDICATOR
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 63.dp,
                    bottom = Spacing.spacing_8,
                    start = Spacing.spacing_16,
                    end = Spacing.spacing_16
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { query ->
                    viewModel.onEvent(HomeUiEvent.SearchChanged(query))
                },
                modifier = Modifier.weight(1f)
            )

            FilterButton(
                isSelected = state.isGenreListVisible,
                onClick = {
                    viewModel.onEvent(HomeUiEvent.ToggleGenreFilter)
                }
            )
        }

        AnimatedVisibility(
            visible = state.isGenreListVisible
        ) {
            LazyRow(
                modifier = Modifier.padding(
                    start = Spacing.spacing_16,
                    end = Spacing.spacing_16,
                    top = Spacing.spacing_8
                )
            ) {
                items(state.genreList) { genre ->
                    GenreListLabel(
                        isSelected = genre.id == state.selectedGenre,
                        onClick = {
                            viewModel.onEvent(
                                HomeUiEvent.GenreSelected(genre.id)
                            )
                        },
                        title = genre.name,
                    )
                }
            }
        }

        Text(
            text = "Movies",
            style = Typography.titleLarge,
            color = YellowPrimary,
            modifier = Modifier.padding(
                start = Spacing.spacing_16,
                top = Spacing.spacing_22
            )
        )

        LazyVerticalGrid(
            state = gridState,
            contentPadding = PaddingValues(Spacing.spacing_16),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_16),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_20),
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.movieList) { movie ->
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.posterPath,
                    releaseDate = movie.releaseDate,
                    genre = movie.genre,
                    isFavorite = false,
                    onFavoriteClick = {},
                    modifier = Modifier,
                    onClick = {}
                )
            }
            if (state.isLoading && state.movieList.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}