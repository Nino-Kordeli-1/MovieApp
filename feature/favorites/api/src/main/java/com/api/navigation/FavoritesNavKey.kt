package com.api.navigation

import androidx.navigation3.runtime.NavKey
import com.navigation.BottomBarDestinations
import com.navigation.BottomBarNavKey
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesNavKey : NavKey, BottomBarNavKey {
    override val bottomBarDestination = BottomBarDestinations.Favorites
}