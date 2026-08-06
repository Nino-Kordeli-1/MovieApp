package com.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.designsystem.Spacing
import com.designsystem.theme.Neutral03DarkGrey
import com.movieapp.ui.R

@Composable
fun OfflineBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Neutral03DarkGrey, shape = RoundedCornerShape(Spacing.spacing_16))
            .padding(Spacing.spacing_16)

    ) { Text(stringResource(R.string.core_ui_check_your_internet_connection)) }
}