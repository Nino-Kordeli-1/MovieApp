package com.ui.components.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.designsystem.Spacing
import com.designsystem.theme.Neutral02DarkestGrey
import com.designsystem.theme.YellowPrimary
import com.movieapp.designsystem.R
import com.navigation.BottomBarDestinations
import com.ui.components.delay_click.safeClick

@Composable
fun NavigationBar(
    currentDestination: BottomBarDestinations?,
    navigator: BottomBarNavigator
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
            .padding(
                start = Spacing.spacing_16,
                end = Spacing.spacing_16,
                top = Spacing.spacing_16,
                bottom = Spacing.spacing_28
            ),
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_16)
    ) {
        NavButtons(
            modifier = Modifier
                .weight(1f)
                .heightIn(Spacing.spacing_38),
            label = stringResource(com.movieapp.ui.R.string.core_ui_home),
            selected = currentDestination == BottomBarDestinations.Home,
            onClick = safeClick { navigator.onNavigate(BottomBarDestinations.Home) },
            iconRes = R.drawable.ic_home
        )
        NavButtons(
            modifier = Modifier.weight(1f),
            label = stringResource(com.movieapp.ui.R.string.core_ui_favorites),
            selected = currentDestination == BottomBarDestinations.Favorites,
            onClick = safeClick { navigator.onNavigate(BottomBarDestinations.Favorites) },
            iconRes = R.drawable.ic_outlined_heart
        )
    }
}

@Composable
fun NavButtons(
    label: String,
    iconRes: Int,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(Spacing.spacing_8),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) YellowPrimary else Neutral02DarkestGrey,
            contentColor = if (selected) Black else White
        )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            modifier = Modifier.size(Spacing.spacing_18),
            tint = if (selected) Black else White
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (selected) Black else White,
            modifier = Modifier.padding(Spacing.spacing_6)
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    NavigationBar(BottomBarDestinations.Home, navigator = {})
    NavigationBar(BottomBarDestinations.Favorites, navigator = {})
}