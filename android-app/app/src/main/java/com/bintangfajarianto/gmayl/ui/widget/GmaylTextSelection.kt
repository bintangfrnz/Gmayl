package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylTextSelection(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = "",
    prefixSubtitle: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val textColor by animateColorAsState(
        targetValue = when {
            !enabled -> GmaylTheme.color.mist50
            else -> GmaylTheme.color.mist100
        },
        label = "text color",
    )

    Column(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = GmaylTheme.typography.contentMediumRegular,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (subtitle.isNotEmpty() && enabled) {
            Text(
                text = "$prefixSubtitle: $subtitle",
                style = GmaylTheme.typography.contentSmallRegular,
                color = GmaylTheme.color.mist70,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
