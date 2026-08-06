package com.impl.navigation


import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.HomeNavKey
import com.impl.screen.home.contract.HomeUiSideEffect
import com.impl.screen.home.screen.HomeScreen
import com.impl.screen.home.vm.HomeViewModel
import com.navigation.requireNavigator
import org.koin.androidx.compose.koinViewModel

fun EntryProviderScope<NavKey>.homeEntry() {
    entry<HomeNavKey> {
        val viewModel: HomeViewModel = koinViewModel()
        val navigator = requireNavigator()

        LaunchedEffect(viewModel) {
            viewModel.navigationCommands.collect { command ->
                command.execute(navigator)
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    is HomeUiSideEffect.ShowError -> {
                    }
                }
            }
        }
        HomeScreen(viewModel = viewModel)
    }
}