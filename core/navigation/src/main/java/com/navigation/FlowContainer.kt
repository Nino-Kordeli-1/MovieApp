package com.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

@Composable
fun FlowContainer(
    navigator: Navigator,
    entries: List<NavEntry<NavKey>>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        modifier = modifier,
        entries = entries,
        onBack = { navigator.goBack() }
    )
}