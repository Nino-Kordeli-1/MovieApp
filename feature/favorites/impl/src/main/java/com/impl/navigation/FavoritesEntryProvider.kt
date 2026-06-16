package com.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.FavoritesNavKey
import com.impl.screen.favorites.screen.FavoritesScreen
import com.navigation.Navigator

fun EntryProviderScope<NavKey>.favoritesEntry(navigator: Navigator){
    entry<FavoritesNavKey>{
        FavoritesScreen()
    }
}