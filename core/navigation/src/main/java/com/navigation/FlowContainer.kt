package com.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

private const val ANIM_DURATION = 300

private fun forwardTransition(): ContentTransform =
    (slideInHorizontally(tween(ANIM_DURATION)) { it } + fadeIn(tween(ANIM_DURATION)))
        .togetherWith(slideOutHorizontally(tween(ANIM_DURATION)) { -it } + fadeOut(tween(ANIM_DURATION)))

private fun backTransition(): ContentTransform =
    (slideInHorizontally(tween(ANIM_DURATION)) { -it } + fadeIn(tween(ANIM_DURATION)))
        .togetherWith(slideOutHorizontally(tween(ANIM_DURATION)) { it } + fadeOut(tween(ANIM_DURATION)))

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