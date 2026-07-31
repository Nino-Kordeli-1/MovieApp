package com.api.navigation

import androidx.navigation3.runtime.NavKey
import com.navigation.BottomBarVisibility
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesNavKey : NavKey, BottomBarVisibility {
    override val showBottomBar: Boolean
        get() = true
}