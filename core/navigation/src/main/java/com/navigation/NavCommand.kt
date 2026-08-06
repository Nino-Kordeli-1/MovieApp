package com.navigation

import androidx.navigation3.runtime.NavKey

sealed interface NavCommand {

    fun execute(navigator: Navigator)

    data class Navigate(private val key: NavKey) : NavCommand {
        override fun execute(navigator: Navigator) {
            navigator.navigate(key)
        }
    }

    data object Back : NavCommand {
        override fun execute(navigator: Navigator) {
            navigator.goBack()
        }
    }

    data class Replace(private val key: NavKey) : NavCommand {
        override fun execute(navigator: Navigator) {
            navigator.replace(key)
        }
    }

    data class NavigateAndClearStack(private val key: NavKey) : NavCommand {
        override fun execute(navigator: Navigator) {
            navigator.navigateAndClearStack(key)
        }
    }
}