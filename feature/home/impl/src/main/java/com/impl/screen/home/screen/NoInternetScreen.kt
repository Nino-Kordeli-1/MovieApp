package com.impl.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import com.designsystem.theme.Neutral07LightestGrey
import com.designsystem.theme.Typography
import com.movieapp.impl.home.R
import com.ui.components.button.RefreshButton

@Composable
fun NoInternetScreen(
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(com.movieapp.designsystem.R.drawable.ic_warning),
                contentDescription = null,
                tint = Neutral07LightestGrey
            )

            Text(
                text = stringResource(R.string.feature_home_impl_data_can_t_be_loaded),
                modifier = Modifier.padding(top = Spacing.spacing_28),
                color = Neutral07LightestGrey,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.feature_home_impl_internet_connection_or_some_other_server_error),
                modifier = Modifier.padding(
                    top = Spacing.spacing_24,
                    start = Spacing.spacing_63,
                    end = Spacing.spacing_63
                ),
                color = Neutral07LightestGrey,
                textAlign = TextAlign.Center
            )

            RefreshButton(onRetry = onRetry)
        }
    }
}