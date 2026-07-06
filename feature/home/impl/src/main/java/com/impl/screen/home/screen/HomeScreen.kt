package com.impl.screen.home.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral08Whisper
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary
import com.impl.screen.home.components.FilterButton
import com.impl.screen.home.components.GenreListLabel
import com.impl.screen.home.components.SearchBar
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.vm.HomeViewModel
import com.movieapp.impl.home.R
import com.ui.components.movie_card.MovieCard
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    var headerVisible by remember { mutableStateOf(true) }
    var lastIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(gridState.firstVisibleItemIndex) {
        val current = gridState.firstVisibleItemIndex

        headerVisible = when {
            current == 0 -> true
            current > lastIndex -> false
            current < lastIndex -> true
            else -> headerVisible
        }

        lastIndex = current
    }

    LaunchedEffect(state.searchQuery, state.selectedGenre) {
        gridState.scrollToItem(0)
        headerVisible = true
    }

    val isEmptyState =
        state.movieList.isEmpty() &&
                !state.isLoading &&
                state.searchQuery.isNotEmpty()

    val shouldLoadMore by remember {
        derivedStateOf {
            val last = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            last.index >= gridState.layoutInfo.totalItemsCount - 2
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && state.hasMorePages) {
            viewModel.onEvent(HomeUiEvent.LoadNextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
    ) {

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                top = Spacing.spacing_140,
                start = Spacing.spacing_16,
                end = Spacing.spacing_16,
                bottom = Spacing.spacing_16
            ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_16),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_20),
            modifier = Modifier.fillMaxSize()
        ) {

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(R.string.feature_home_impl_movies),
                    style = Typography.titleLarge,
                    color = YellowPrimary,
                    modifier = Modifier.padding(
                        bottom = Spacing.spacing_8
                    )
                )
            }

            items(state.movieList) { movie ->
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.posterPath,
                    releaseDate = movie.releaseDate,
                    genre = movie.genre,
                    isFavorite = false,
                    onFavoriteClick = {},
                    onClick = {}
                )
            }

            if (state.isLoading && state.movieList.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.spacing_16),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = headerVisible,
            enter = fadeIn(tween(150)) + slideInVertically { -it },
            exit = fadeOut(tween(120)) + slideOutVertically { -it }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Spacing.spacing_63,
                        start = Spacing.spacing_16,
                        end = Spacing.spacing_16
                    )
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = {
                            viewModel.onEvent(HomeUiEvent.SearchChanged(it))
                        },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(Spacing.spacing_8))

                    if (state.isSearchActive) {
                        Text(
                            text = "Cancel",
                            color = Neutral08Whisper,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(HomeUiEvent.SearchCancelled)
                            }
                        )
                    } else {
                        FilterButton(
                            isSelected = state.isGenreListVisible,
                            onClick = {
                                viewModel.onEvent(HomeUiEvent.ToggleGenreFilter)
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = state.isGenreListVisible && !state.isSearchActive
                ) {
                    LazyRow(
                        modifier = Modifier.padding(top = Spacing.spacing_8)
                    ) {
                        items(state.genreList) { genre ->
                            GenreListLabel(
                                isSelected = genre.id == state.selectedGenre,
                                onClick = {
                                    viewModel.onEvent(HomeUiEvent.GenreSelected(genre.id))
                                },
                                title = genre.name
                            )
                        }
                    }
                }
            }
        }

        if (isEmptyState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(
                        com.movieapp.designsystem.R.drawable.ic_no_results
                    ),
                    contentDescription = null,
                    tint = Neutral02DarkestGrey
                )

                Text(
                    text = "No movies found",
                    modifier = Modifier.padding(top = Spacing.spacing_24),
                    color = Neutral02DarkestGrey,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}