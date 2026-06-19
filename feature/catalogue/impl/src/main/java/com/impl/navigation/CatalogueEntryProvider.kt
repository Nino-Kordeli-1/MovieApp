package com.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.CatalogueNavKey
import com.impl.screen.catalogue.screen.CatalogueScreen
import com.navigation.Navigator

fun EntryProviderScope<NavKey>.catalogueEntry(navigator: Navigator) {
    entry<CatalogueNavKey> {
        CatalogueScreen()
    }
}