package com.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import com.api.HomeNavKey
import com.api.navigation.FavoritesNavKey
import com.designsystem.theme.MovieAppTheme
import com.domain.observer.ConnectivityObserver
import com.impl.navigation.detailsEntry
import com.impl.navigation.favoritesEntry
import com.impl.navigation.homeEntry
import com.impl.screen.splash.screen.SplashScreen
import com.navigation.FlowContainer
import com.navigation.LocalNavigator
import com.navigation.NavigationState
import com.navigation.Navigator
import com.navigation.rememberNavigationState
import com.navigation.requireNavigator
import com.navigation.toEntries
import com.ui.components.bottom_bar.BottomBarDestinations
import com.ui.components.bottom_bar.NavigationBar
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {

    var showSplash by rememberSaveable {
        mutableStateOf(true)
    }

    val connectivityObserver = koinInject<ConnectivityObserver>()
    val isConnected by connectivityObserver.observe()
        .collectAsStateWithLifecycle(initialValue = true)

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
        topLevelKeys = setOf(FavoritesNavKey)
    )

    val navigator = remember(navigationState) {
        Navigator(navigationState)
    }

    val entryProvider = entryProvider {
        detailsEntry()
        homeEntry()
        favoritesEntry()
    }

    val entries = navigationState.toEntries(entryProvider)

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0),
            bottomBar = {
                if (isConnected) {
                    BottomBarContent(navigationState)
                }
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
private fun BottomBarContent(navigationState: NavigationState) {
    val currentKey = navigationState.currentKey
    val navigator = requireNavigator()

    val currentDestination = when (currentKey) {
        is HomeNavKey -> BottomBarDestinations.Home
        is FavoritesNavKey -> BottomBarDestinations.Favorites
        else -> return
    }

    NavigationBar(
        currentDestination = currentDestination,
        navigator = { destination ->
            when (destination) {
                BottomBarDestinations.Home -> navigator.navigate(HomeNavKey)
                BottomBarDestinations.Favorites -> navigator.navigate(FavoritesNavKey)
            }
        }
    )
}