package com.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavKey(
    val movieId: Int
) : NavKey {
}