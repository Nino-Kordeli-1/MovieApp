package com.impl.screen.details.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.designsystem.Spacing
import com.designsystem.theme.MovieAppTheme
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral06LightGrey
import com.designsystem.theme.Neutral08Whisper
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary
import com.impl.screen.details.contract.DetailsUiEvent
import com.impl.screen.details.contract.DetailsUiState
import com.impl.screen.details.vm.DetailsViewModel
import com.model.MovieDetailsUiModel
import com.model.MovieGenreUiModel
import com.movieapp.impl.details.R
import com.ui.components.header.Header
import com.ui.components.label.InfoLabel
import com.ui.components.label.RatingLabel
import com.ui.components.loader.MovieLoadingIndicator
import com.ui.formatter.toRuntimeString

@SuppressLint("DefaultLocale")
@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailsScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun DetailsScreenContent(
    state: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit
) {
    if (state.isLoading && state.movie == null) {
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

    if (state.error != null && state.movie == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Neutral01Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.error,
                color = Neutral08Whisper
            )
        }
        return
    }

    val movie = state.movie ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black)
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            showBackButton = true,
            text = stringResource(R.string.feature_details_impl_detail),
            onClick = {
                onEvent(
                    DetailsUiEvent.BackClicked
                )
            }
        )

        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w780${movie.backdropPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(
                    ratio = 163.5f / 226f,
                ),
            contentScale = ContentScale.Crop,
            error = {
                Image(
                    painter = painterResource(com.movieapp.designsystem.R.drawable.ic_no_photo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Neutral02DarkestGrey)
                        .padding(Spacing.spacing_63),
                    contentScale = ContentScale.Fit
                )
            }
        )

        Column(
            modifier = Modifier.padding(
                horizontal = Spacing.spacing_16,
                vertical = Spacing.spacing_16
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.spacing_10),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = movie.title,
                    style = Typography.titleLarge,
                    color = Neutral08Whisper,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        onEvent(
                            DetailsUiEvent.FavoriteClicked
                        )
                    }
                ) {

                    Icon(
                        painter = painterResource(
                            if (movie.isFavorite) {
                                com.movieapp.designsystem.R.drawable.ic_filled_heart_big
                            } else {
                                com.movieapp.designsystem.R.drawable.ic_heart_outline_big
                            }
                        ),
                        contentDescription = null,
                        tint = YellowPrimary
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(
                    Spacing.spacing_12
                )
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(
                    Spacing.spacing_8
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                item {
                    RatingLabel(
                        rating = movie.voteAverage
                    )
                }
                if (movie.genres.isNotEmpty()) {
                    item {
                        InfoLabel(
                            label = movie.genres.first().name
                        )
                    }
                }
                item {
                    InfoLabel(
                        label = movie.runtime.toRuntimeString()
                    )
                }
                if (movie.releaseDate.length >= 4) {
                    item {
                        InfoLabel(
                            label = movie.releaseDate.take(4)
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(
                    Spacing.spacing_16
                )
            )

            Text(
                text = movie.overview,
                style = Typography.bodyMedium,
                color = Neutral06LightGrey,
                lineHeight = Typography.bodyMedium.lineHeight
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    MovieAppTheme {
        DetailsScreenContent(
            state = DetailsUiState(
                movie = MovieDetailsUiModel(
                    id = 1,
                    title = "Sample Movie",
                    posterPath = "/22dj38IckjzEEUZwN1tPU5VJ1qq.jpg",
                    releaseDate = "2024",
                    isFavorite = false,
                    backdropPath = "/4z9ijhgEthfRHShoOvMaBlpciXS.jpg",
                    genres = listOf(
                        MovieGenreUiModel(
                            id = 12,
                            name = "action"
                        )
                    ),
                    originalTitle = "Star Wars Collection",
                    overview = "overview",
                    voteAverage = 8.2,
                    runtime = 268,
                )
            ),
            onEvent = {}
        )
    }
}