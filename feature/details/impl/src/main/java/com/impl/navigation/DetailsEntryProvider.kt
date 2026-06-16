package com.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.api.navigation.DetailsNavKey
import com.impl.screen.details.screen.DetailsScreen
import com.navigation.Navigator

fun EntryProviderScope<NavKey>.detailsEntry(navigator: Navigator) {
    entry<DetailsNavKey> {
        DetailsScreen()
    }
}