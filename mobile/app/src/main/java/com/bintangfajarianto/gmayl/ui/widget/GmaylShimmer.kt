package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
private fun gmaylShimmerBrush(): Brush {
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

@Composable
fun ShimmerListItem(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(color = GmaylTheme.color.mist10)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),

        ) {
        Spacer(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(gmaylShimmerBrush()),
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(18.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(gmaylShimmerBrush()),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(gmaylShimmerBrush()),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewShimmerListItem() {
    ShimmerListItem()
}
