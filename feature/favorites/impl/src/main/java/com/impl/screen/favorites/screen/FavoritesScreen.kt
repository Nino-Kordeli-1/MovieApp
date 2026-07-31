package com.impl.screen.favorites.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.theme.Neutral01Black
import com.impl.screen.favorites.contract.FavoritesUiEvent
import com.impl.screen.favorites.vm.FavoritesViewModel
import com.movieapp.impl.favorites.R
import com.ui.components.empty_state.EmptyScreen
import com.ui.components.header.Header
import com.ui.components.movie_grid.MovieGrid

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading && state.favorites.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (state.favorites.isEmpty()) {
        EmptyScreen(text = stringResource(R.string.feature_favorites_impl_no_movies_added_yet))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
    ) {
        Header(
            showBackButton = false,
            text = stringResource(R.string.feature_favorites_impl_favorites)
        ) { }

        MovieGrid(
            title = null,
            isLoading = false,
            gridState = state.gridState,
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