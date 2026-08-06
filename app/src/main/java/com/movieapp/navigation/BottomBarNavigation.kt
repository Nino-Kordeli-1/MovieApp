package com.movieapp.navigation

import androidx.navigation3.runtime.NavKey
import com.api.HomeNavKey
import com.api.navigation.FavoritesNavKey
import com.navigation.BottomBarDestinations

fun BottomBarDestinations.toNavKey(): NavKey = when (this){
    BottomBarDestinations.Home -> HomeNavKey
    BottomBarDestinations.Favorites -> FavoritesNavKey
}