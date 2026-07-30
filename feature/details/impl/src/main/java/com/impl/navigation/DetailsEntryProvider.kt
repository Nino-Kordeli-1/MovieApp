package com.impl.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.DetailsNavKey
import com.impl.screen.details.screen.DetailsScreen
import com.impl.screen.details.vm.DetailsViewModel
import com.navigation.requireNavigator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun EntryProviderScope<NavKey>.detailsEntry() {
    entry<DetailsNavKey> { key ->
        val viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(key.movieId) })
        val navigator = requireNavigator()

        LaunchedEffect(viewModel) {
            viewModel.navigationCommands.collect { command ->
                command.execute(navigator)
            }
        }

        DetailsScreen(viewModel = viewModel)
    }
}