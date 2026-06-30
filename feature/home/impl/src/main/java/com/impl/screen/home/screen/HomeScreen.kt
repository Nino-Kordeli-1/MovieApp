package com.impl.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.YellowPrimary
import com.impl.screen.home.components.SearchBar
import com.impl.screen.home.contract.HomeUiEvent
import com.impl.screen.home.vm.HomeViewModel
import com.movieapp.designsystem.R
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
                    top = Spacing.spacing_36,
                    bottom = Spacing.spacing_22,
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

            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = null,
                tint = YellowPrimary,
                modifier = Modifier
                    .padding(start = Spacing.spacing_8)
            )
        }

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
                    genre = "",
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