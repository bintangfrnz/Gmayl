package com.bintangfajarianto.gmayl.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun GmaylTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalGmaylColor provides GmaylTheme.color,
            LocalGmaylTypography provides GmaylTheme.typography,
        ) {
            content()
        }
    }
}

object GmaylTheme {
    val color: GmaylColor
        @Composable
        @ReadOnlyComposable
        get() = LocalGmaylColor.current

    val typography: GmaylTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalGmaylTypography.current
}
