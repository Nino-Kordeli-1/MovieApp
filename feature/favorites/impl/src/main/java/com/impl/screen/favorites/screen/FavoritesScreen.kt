package com.impl.screen.favorites.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.theme.Neutral01Black
import com.impl.screen.favorites.contract.FavoritesUiEvent
import com.impl.screen.favorites.vm.FavoritesViewModel
import com.ui.components.empty_state.EmptyScreen
import com.ui.components.header.Header
import com.ui.components.movie_grid.MovieGrid

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    if (state.isLoading && state.favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (state.favorites.isEmpty()) {
        EmptyScreen(text = "No movies added yet")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
    ) {
        Header(
            showBackButton = false,
            text = "Favorites"
        ) { }

        MovieGrid(
            title = null,
            isLoading = false,
            gridState = gridState,
            movies = state.favorites,
            onMovieClick = { movieId ->
                viewModel.onEvent(
                    FavoritesUiEvent.MovieClicked(movieId)
                )
            },
            onFavoriteClick = { movie ->
                viewModel.onEvent(
                    FavoritesUiEvent.RemoveFavorite(movie.id)
                )
            }
        )
    }
}