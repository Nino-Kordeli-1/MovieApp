package com.api

import androidx.navigation3.runtime.NavKey
import com.navigation.BottomBarVisibility
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavKey : NavKey, BottomBarVisibility {
    override val showBottomBar: Boolean
        get() = true
}