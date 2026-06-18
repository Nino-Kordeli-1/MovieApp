package com.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.api.CatalogueNavKey
import com.api.navigation.FavoritesNavKey
import com.api.navigation.SplashNavKey
import com.impl.navigation.catalogueEntry
import com.impl.navigation.detailsEntry
import com.impl.navigation.favoritesEntry
import com.impl.navigation.splashEntry
import com.movieapp.ui.theme.MovieAppTheme
import com.navigation.NavigationState
import com.navigation.Navigator
import com.navigation.rememberNavigationState
import com.navigation.toEntries
import com.ui.components.bottom_bar.BottomBarDestinations
import com.ui.components.bottom_bar.NavigationBar

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
    val navigationState = rememberNavigationState(
        startKey = SplashNavKey,
        topLevelKeys = setOf(
            FavoritesNavKey,
            CatalogueNavKey,
        )
    )

    val navigator = remember(navigationState) { Navigator(navigationState) }

    val entryProvider = entryProvider {
        splashEntry(navigator)
        detailsEntry(navigator)
        catalogueEntry(navigator)
        favoritesEntry(navigator)
    }

    val entries = navigationState.toEntries(entryProvider)

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0),
        bottomBar = { BottomBarContent(navigationState, navigator) },
    ) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            entries = entries,
            onBack = { navigator.goBack() }
        )
    }
}

@Composable
private fun BottomBarContent(navigationState: NavigationState, navigator: Navigator) {
    val currentKey = navigationState.currentKey

    val currentDestination = when (currentKey) {
        is CatalogueNavKey -> BottomBarDestinations.Catalogue
        is FavoritesNavKey -> BottomBarDestinations.Favorites
        else -> return
    }

    NavigationBar(
        currentDestination = currentDestination,
        navigator = { destination ->
            when (destination) {
                BottomBarDestinations.Catalogue -> navigator.navigate(CatalogueNavKey)
                BottomBarDestinations.Favorites -> navigator.navigate(FavoritesNavKey)
            }
        }
    )
}