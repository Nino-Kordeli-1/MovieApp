package com.impl.screen.home.components

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun CustomPaging(
    gridState: LazyGridState,
    isLoading: Boolean,
    hasMorePages: Boolean,
    itemCount: Int,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore by remember(gridState, itemCount) {
        derivedStateOf {
            if (itemCount == 0) {
                return@derivedStateOf false
            }

            val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisible.index >= gridState.layoutInfo.totalItemsCount - 2
        }
    }

    LaunchedEffect(shouldLoadMore,isLoading,hasMorePages) {
        if (shouldLoadMore && hasMorePages && !isLoading) {
            onLoadMore()
        }
    }
}