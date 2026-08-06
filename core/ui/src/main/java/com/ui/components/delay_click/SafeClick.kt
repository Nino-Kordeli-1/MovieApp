package com.ui.components.delay_click

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

private object ClickDebounce {
    @Volatile
    var lastClickTime = 0L
}

fun Modifier.safeClick(
    debounceTime: Long = 500L,
    onClick: () -> Unit
): Modifier = this.clickable {
    val now = System.currentTimeMillis()
    if (now - ClickDebounce.lastClickTime >= debounceTime) {
        ClickDebounce.lastClickTime = now
        onClick()
    }
}

fun safeClick(
    debounceTime: Long = 250L,
    onClick: () -> Unit
): () -> Unit = {
    val now = System.currentTimeMillis()
    if (now - ClickDebounce.lastClickTime >= debounceTime) {
        ClickDebounce.lastClickTime = now
        onClick()
    }
}