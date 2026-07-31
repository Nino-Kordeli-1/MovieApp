package com.navigation

import androidx.navigation3.runtime.NavKey

val NavKey.shouldShowBottomBar: Boolean
    get() = this is BottomBarVisibility && showBottomBar