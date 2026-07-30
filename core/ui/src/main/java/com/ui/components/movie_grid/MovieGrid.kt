package com.ui.components.movie_grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.designsystem.Spacing
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary
import com.model.MovieUiModel
import com.ui.components.movie_card.MovieCard

@Composable
fun MovieGrid(
    title: String? = null,
    isLoading: Boolean = false,
    gridState: LazyGridState,
    movies: List<MovieUiModel>,
    onMovieClick: (movieId: Int) -> Unit,
    onFavoriteClick: (movie: MovieUiModel) -> Unit
) {

    val uniqueMovies = movies.distinctBy { it.id }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = if (title != null) {
                Spacing.spacing_63
            } else {
                Spacing.spacing_16
            },
            start = Spacing.spacing_12,
            end = Spacing.spacing_12,
            bottom = Spacing.spacing_16
        ),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_16),
        verticalArrangement = Arrangement.spacedBy(Spacing.spacing_20),
        modifier = Modifier.fillMaxSize()
    ) {
        if (title != null) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = title,
                    style = Typography.titleLarge,
                    color = YellowPrimary,
                    modifier = Modifier.padding(
                        bottom = Spacing.spacing_8
                    )
                )
            }
        }

        items(items = uniqueMovies, key = { movie -> movie.id }) { movie ->
            MovieCard(
                title = movie.title,
                posterUrl = movie.posterPath,
                releaseDate = movie.releaseDate,
                genre = movie.genre,
                isFavorite = movie.isFavorite,
                onFavoriteClick = {
                    onFavoriteClick(movie)
                },
                onClick = {
                    onMovieClick(movie.id)
                }
            )
        }

        if (isLoading && movies.isNotEmpty()) {
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
}