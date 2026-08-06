package com.impl.screen.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.designsystem.Spacing
import com.designsystem.theme.Neutral01Black
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000.milliseconds)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral01Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(com.movieapp.designsystem.R.drawable.imdb_logo),
            contentDescription = null,
            modifier = Modifier.width(Spacing.spacing_63).height(Spacing.spacing_34)
        )
    }
}