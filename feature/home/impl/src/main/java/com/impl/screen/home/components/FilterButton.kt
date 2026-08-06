package com.impl.screen.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.designsystem.Spacing
import com.movieapp.designsystem.R
import com.ui.components.delay_click.safeClick

@Composable
fun FilterButton(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Icon(
        painter =
            if (isSelected)
                painterResource(R.drawable.ic_filled_filter)
            else
                painterResource(R.drawable.ic_filter),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier
            .padding(start = Spacing.spacing_8)
            .safeClick(onClick = onClick)
    )
}