package com.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.DetailsNavKey
import com.api.navigation.FavoritesNavKey
import com.impl.screen.favorites.contract.FavoritesUiSideEffect
import com.impl.screen.favorites.screen.FavoritesScreen
import com.impl.screen.favorites.vm.FavoritesViewModel
import com.navigation.Navigator
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.favoritesEntry(navigator: Navigator) {
    entry<FavoritesNavKey> {
        val viewModel: FavoritesViewModel = koinViewModel()

        LaunchedEffect(Unit) {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is FavoritesUiSideEffect.NavigateToDetails -> {
                        navigator.navigate(DetailsNavKey(effect.movieId))
                    }
                }
            }
        }
        FavoritesScreen(viewModel = viewModel)
    }
}