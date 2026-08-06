package com.ui.base.vm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun <State, Event, SideEffect> BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<State, Event, SideEffect>,
    applySystemBarsPadding: Boolean = true,
    onSideEffect: (SideEffect) -> Unit = {},
    content: @Composable (
        state: State,
        onEvent: (Event) -> Unit
    ) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            onSideEffect(effect)
        }
    }

    val resolvedModifier = if (applySystemBarsPadding) {
        modifier.statusBarsPadding()
    } else {
        modifier
    }

    Box(modifier = resolvedModifier) {
        content(state, viewModel::onEvent)
    }
}
