package com.ui.components.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigationBar(
    currentDestination: BottomBarDestinations?,
    navigator: BottomBarNavigator
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NavButtons(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 38.dp),
            label = "Home",
            selected = currentDestination == BottomBarDestinations.Catalogue,
            onClick = { navigator.onNavigate(BottomBarDestinations.Catalogue) }
        )
        NavButtons(
            modifier = Modifier.weight(1f),
            label = "Favorites",
            selected = currentDestination == BottomBarDestinations.Favorites,
            onClick = { navigator.onNavigate(BottomBarDestinations.Favorites) }
        )
    }
}

@Composable
fun NavButtons(
    label: String,
    //iconRes: Int,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) White else Black,
            contentColor = if (selected) Black else White
        )
    ) {
        /*Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            modifier = Modifier.size(18.dp),
            tint = if (selected) Black else White
        )*/
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (selected) Black else White
        )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    NavigationBar(BottomBarDestinations.Catalogue, navigator = BottomBarNavigator(function = {}))
    NavigationBar(BottomBarDestinations.Favorites, navigator = BottomBarNavigator(function = {}))
}