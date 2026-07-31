package com.movieapp.vm

import androidx.lifecycle.viewModelScope
import com.domain.observer.ConnectivityObserver
import com.movieapp.contract.MainActivityUiState
import com.ui.base.vm.BaseViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val connectivityObserver: ConnectivityObserver
) : BaseViewModel<MainActivityUiState, Nothing, Nothing>(MainActivityUiState()) {

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collect { isConnected ->
                updateState { it.copy(isConnected = isConnected) }
            }
        }
    }
}