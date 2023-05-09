package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun gmaylShimmerBrush(): Brush {
    val gradient = listOf(
        GmaylTheme.color.mist40,
        Color(0xFFe1e1e1),
        GmaylTheme.color.mist40,
    )

    val transition = rememberInfiniteTransition()

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing,
            ),
        ),
    )

    return Brush.linearGradient(
        colors = gradient,
        start = Offset(10f, 10f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value,
        ),
    )
}
