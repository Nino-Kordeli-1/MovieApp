package com.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import com.api.HomeNavKey
import com.api.navigation.FavoritesNavKey
import com.designsystem.theme.MovieAppTheme
import com.designsystem.theme.Neutral01Black
import com.impl.navigation.detailsEntry
import com.impl.navigation.favoritesEntry
import com.impl.navigation.homeEntry
import com.impl.navigation.splashEntry
import com.impl.screen.splash.screen.SplashScreen
import com.movieapp.contract.MainActivityUiState
import com.movieapp.vm.MainActivityViewModel
import com.navigation.FlowContainer
import com.navigation.LocalNavigator
import com.navigation.NavigationState
import com.navigation.Navigator
import com.navigation.rememberNavigationState
import com.navigation.requireNavigator
import com.navigation.shouldShowBottomBar
import com.navigation.toEntries
import com.ui.components.bottom_bar.BottomBarDestinations
import com.ui.components.bottom_bar.NavigationBar
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val viewModel: MainActivityViewModel = koinViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                AppNavigation(state)
            }
        }
    }
}

@Composable
private fun AppNavigation(state: MainActivityUiState) {

    var showSplash by rememberSaveable {
        mutableStateOf(true)
    }

    if (showSplash) {
        SplashScreen(
            onFinished = {
                showSplash = false
            }
        )
        return
    }

    val navigationState = rememberNavigationState(
        startKey = HomeNavKey,
        topLevelKeys = setOf(FavoritesNavKey, HomeNavKey)
    )

    val navigator = remember(navigationState) {
        Navigator(navigationState)
    }

    val entryProvider = entryProvider {
        detailsEntry()
        homeEntry()
        favoritesEntry()
        splashEntry()
    }

    val entries = navigationState.toEntries(entryProvider)

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Scaffold(
            containerColor = Neutral01Black,
            contentWindowInsets = WindowInsets(0),
            bottomBar = {
                BottomBarContent(
                    navigationState = navigationState,
                    isConnected = state.isConnected
                )
            }
        ) { padding ->
            FlowContainer(
                modifier = Modifier.padding(padding),
                navigator = navigator,
                entries = entries
            )
        }
    }
}

@Composable
private fun BottomBarContent(
    navigationState: NavigationState,
    isConnected: Boolean
) {
    val navigator = requireNavigator()

    val currentDestination = when (navigationState.currentKey) {
        is HomeNavKey -> BottomBarDestinations.Home
        is FavoritesNavKey -> BottomBarDestinations.Favorites
        else -> null
    }

    var lastDestination by remember {
        mutableStateOf(currentDestination)
    }

    if (currentDestination != null) {
        lastDestination = currentDestination
    }

    AnimatedVisibility(
        visible = navigationState.currentKey.shouldShowBottomBar && isConnected,
        enter = fadeIn(tween(250)),
        exit = fadeOut(tween(250))
    ) {
        lastDestination?.let { destination ->
            NavigationBar(
                currentDestination = destination,
                navigator = { target ->
                    when (target) {
                        BottomBarDestinations.Home ->
                            navigator.navigate(HomeNavKey)

                        BottomBarDestinations.Favorites ->
                            navigator.navigate(FavoritesNavKey)
                    }
                }
            )
        }
    }
}