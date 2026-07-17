package com.ui.components.header

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.designsystem.Spacing
import com.designsystem.theme.Neutral08Whisper
import com.designsystem.theme.Typography

@Composable
fun Header(
    showBackButton: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(Spacing.spacing_56),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(com.movieapp.designsystem.R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    tint = Neutral08Whisper
                )
            }
        } else {
            Spacer(
                modifier = Modifier.size(Spacing.spacing_44)
            )
        }

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium,
            color = Neutral08Whisper,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.size(Spacing.spacing_44)
        )
    }
}