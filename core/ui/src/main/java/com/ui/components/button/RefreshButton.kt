package com.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.YellowPrimary
import com.movieapp.ui.R

@Composable
fun RefreshButton(
    onRetry: () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(top = Spacing.spacing_24)
            .height(Spacing.spacing_44)
            .width(Spacing.spacing_134),
        colors = ButtonDefaults.buttonColors(
            containerColor = YellowPrimary,
            contentColor = Neutral01Black
        ),
        shape = RoundedCornerShape(Spacing.spacing_16),
        contentPadding = PaddingValues(
            horizontal = Spacing.spacing_20
        ),
        onClick = onRetry
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.core_ui_refresh), fontWeight = FontWeight.Bold)
            Icon(
                painter = painterResource(com.movieapp.designsystem.R.drawable.ic_refresh),
                contentDescription = null
            )
        }
    }
}