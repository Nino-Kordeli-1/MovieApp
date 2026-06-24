package com.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.SplashNavKey
import com.impl.screen.splash.screen.SplashScreen
import com.navigation.Navigator

fun EntryProviderScope<NavKey>.splashEntry(navigator: Navigator) {
    entry<SplashNavKey> {
        SplashScreen(navigator = navigator)
    }
}