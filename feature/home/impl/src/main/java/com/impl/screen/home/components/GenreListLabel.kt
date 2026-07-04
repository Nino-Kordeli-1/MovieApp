package com.impl.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral08Whisper
import com.designsystem.theme.Typography
import com.designsystem.theme.YellowPrimary
import com.movieapp.impl.home.R

@Composable
fun GenreListLabel(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }) {
        Text(
            style = Typography.labelSmall,
            text = title,
            color = if (isSelected) Neutral01Black else Neutral08Whisper,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(/*top = Spacing.spacing_10,*/ end = Spacing.spacing_10)
                .background(
                    color = if (isSelected) YellowPrimary else Color.Transparent,
                    shape = RoundedCornerShape(Spacing.spacing_22),
                )
                .border(
                    width = if (isSelected) Spacing.spacing_0 else Spacing.spacing_0_5,
                    color = if (isSelected) YellowPrimary else Color.White,
                    shape = RoundedCornerShape(Spacing.spacing_22)
                )
                .padding(horizontal = Spacing.spacing_12, vertical = Spacing.spacing_4)
        )
    }
}

@Preview
@Composable
fun GenreListLabelPreview() {
    GenreListLabel(
        stringResource(R.string.feature_home_impl_genre),
        isSelected = false,
        onClick = {}
    )
}