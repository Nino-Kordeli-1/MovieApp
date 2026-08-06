package com.api.navigation

import androidx.navigation3.runtime.NavKey
import com.navigation.BottomBarVisibility
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavKey(
    val movieId: Int
) : NavKey, BottomBarVisibility {
    override val showBottomBar: Boolean
        get() = false
}