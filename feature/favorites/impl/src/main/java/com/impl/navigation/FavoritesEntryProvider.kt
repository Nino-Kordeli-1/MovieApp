package com.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.FavoritesNavKey
import com.impl.screen.favorites.screen.FavoritesScreen
import com.impl.screen.favorites.vm.FavoritesViewModel
import com.navigation.requireNavigator
import org.koin.compose.viewmodel.koinViewModel

fun EntryProviderScope<NavKey>.favoritesEntry() {
    entry<FavoritesNavKey> {
        val viewModel: FavoritesViewModel = koinViewModel()
        val navigator = requireNavigator()

        LaunchedEffect(viewModel) {
            viewModel.navigationCommands.collect { command ->
                command.execute(navigator)
            }
        }

        FavoritesScreen(viewModel = viewModel)
    }
}