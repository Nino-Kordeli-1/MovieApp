package com.api

import androidx.navigation3.runtime.NavKey
import com.navigation.BottomBarDestinations
import com.navigation.BottomBarNavKey
import com.navigation.BottomBarVisibility
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavKey : NavKey, BottomBarVisibility, BottomBarNavKey {
    override val showBottomBar: Boolean
        get() = true
    override val bottomBarDestination = BottomBarDestinations.Home
}