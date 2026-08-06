package com.ui.components.movie_card

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.designsystem.Spacing
import com.designsystem.theme.ShimmerDarkGrey
import com.designsystem.theme.ShimmerHighlight
import com.designsystem.theme.ShimmerLighterGrey

fun Modifier.shimmer() = composed {

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            ShimmerDarkGrey,
            ShimmerLighterGrey,
            ShimmerHighlight,
            ShimmerLighterGrey,
            ShimmerDarkGrey
        ),
        start = Offset.Zero,
        end = Offset(translateAnim.value, translateAnim.value)
    )

    background(
        brush = brush,
        shape = RoundedCornerShape(Spacing.spacing_16)
    )
}