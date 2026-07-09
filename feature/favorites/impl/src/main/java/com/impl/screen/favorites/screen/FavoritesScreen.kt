package com.impl.screen.favorites.screen

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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Typography
import com.impl.screen.favorites.contract.FavoritesUiEvent
import com.impl.screen.favorites.vm.FavoritesViewModel
import com.ui.components.movie_card.MovieCard

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Neutral01Black),
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
                text = "No movies added yet",
                modifier = Modifier.padding(top = Spacing.spacing_24),
                color = Neutral02DarkestGrey,
                style = Typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
            .padding(top = Spacing.spacing_20)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Spacing.spacing_10),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Favorite Movies", modifier = Modifier.padding(bottom = Spacing.spacing_10))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Spacing.spacing_16),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_16),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_20),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = state.favorites,
                key = { movie -> movie.id }
            ) { movie ->
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.posterPath,
                    releaseDate = movie.releaseDate,
                    genre = movie.genre,
                    isFavorite = true,
                    onFavoriteClick = {
                        viewModel.onEvent(FavoritesUiEvent.RemoveFavorite(movie.id))
                    },
                    modifier = Modifier,
                    onClick = {
                        viewModel.onEvent(FavoritesUiEvent.MovieClicked(movie.id))
                    }
                )
            }
        }
    }
}