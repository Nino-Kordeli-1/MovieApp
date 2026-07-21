package com.ui.base.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navigation.NavCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, SideEffect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _navigationCommands = MutableSharedFlow<NavCommand>()
    val navigationCommands = _navigationCommands.asSharedFlow()

    open fun onEvent(event: Event) {}

    protected fun updateState(update: (State) -> State) {
        _state.update(update)
    }

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }

    protected fun navigate(command: NavCommand) {
        viewModelScope.launch {
            _navigationCommands.emit(command)
        }
    }
}