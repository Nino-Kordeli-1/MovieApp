package com.ui.components.empty_state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral03DarkGrey
import com.designsystem.theme.Typography

@Composable
fun EmptyScreen(
    text: String
) {
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
            tint = Neutral03DarkGrey
        )

        Text(
            text = text,
            modifier = Modifier.padding(top = Spacing.spacing_24),
            color = Neutral03DarkGrey,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}