package com.navigation

import androidx.navigation3.runtime.NavKey

interface BottomBarNavKey : NavKey {
    val bottomBarDestination: BottomBarDestinations
}