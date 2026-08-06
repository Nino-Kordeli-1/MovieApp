package com.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.HomeNavKey
import com.api.navigation.SplashNavKey
import com.impl.screen.splash.screen.SplashScreen
import com.navigation.requireNavigator

fun EntryProviderScope<NavKey>.splashEntry() {
    entry<SplashNavKey> {
        val navigator = requireNavigator()
        SplashScreen(
            onFinished = {
                navigator.navigateAndClearStack(HomeNavKey)
            }
        )
    }
}