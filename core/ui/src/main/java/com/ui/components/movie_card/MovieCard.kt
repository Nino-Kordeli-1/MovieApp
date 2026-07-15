package com.ui.components.movie_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.Neutral08Whisper
import com.designsystem.theme.NeutralGrey04Grey
import com.designsystem.theme.YellowPrimary
import com.movieapp.designsystem.R
import com.ui.components.label.CategoryLabel

@Composable
fun MovieCard(
    title: String,
    genre: String,
    releaseDate: String,
    posterUrl: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier.width(Spacing.spacing_160)
    ) {

        Card(
            shape = RoundedCornerShape(Spacing.spacing_16)
        ) {
            Box {
                SubcomposeAsyncImage(
                    model = posterUrl,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spacing.spacing_226),
                    contentScale = ContentScale.Crop,

                    loading = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .shimmer()
                        )
                    },

                    error = {
                        Image(
                            painter = painterResource(R.drawable.ic_no_photo),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Neutral02DarkestGrey)
                                .padding(Spacing.spacing_63),
                            contentScale = ContentScale.Fit
                        )
                    }
                )

                CategoryLabel(
                    title = genre,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(Spacing.spacing_8)
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = title,
                maxLines = 1,
                color = Neutral08Whisper,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = Spacing.spacing_4, start = Spacing.spacing_4)
                    .weight(1f)
            )

            FavoriteButton(
                selected = isFavorite,
                onClick = onFavoriteClick
            )
        }
        Row() {
            Text(
                style = MaterialTheme.typography.bodySmall,
                text = releaseDate.take(4),
                color = NeutralGrey04Grey,
                modifier = Modifier.padding(
                    start = Spacing.spacing_4,
                    top = Spacing.spacing_2
                )
            )
        }
    }
}

@Composable
fun FavoriteButton(
    selected: Boolean,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(
            if (selected)
                R.drawable.ic_filled_heart
            else
                R.drawable.ic_outlined_heart
        ),
        contentDescription = "Favorite",
        tint = YellowPrimary,
        modifier = Modifier
            .padding(top = Spacing.spacing_4, end = Spacing.spacing_4)
    )
}

@Composable
@Preview
fun MovieCardPreview() {
    MovieCard(
        title = "Movie log title",
        genre = "action",
        releaseDate = "1920",
        posterUrl = "",
        isFavorite = false,
        onFavoriteClick = {},
        modifier = Modifier,
        onClick = {},
    )
}