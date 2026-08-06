package com.ui.components.bottom_bar

import com.navigation.BottomBarDestinations

fun interface BottomBarNavigator {
    fun onNavigate(destinations: BottomBarDestinations)
}