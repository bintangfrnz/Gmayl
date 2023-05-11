package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylCopyableText(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    title: String? = null,
    hint: String? = null,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = GmaylTheme.typography.contentSmallRegular,
                color = if (enabled) GmaylTheme.color.mist100 else GmaylTheme.color.mist50,
            )
        }
        GmaylCopyableTextContent(
            value = value,
            hint = hint,
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Composable
private fun GmaylCopyableTextContent(
    value: TextFieldValue,
    hint: String?,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (enabled) Color.White else GmaylTheme.color.mist20,
        label = "background color",
    )
    val iconColor by animateColorAsState(
        targetValue = when {
            !enabled || value.text.isEmpty() -> GmaylTheme.color.mist40
            else -> GmaylTheme.color.mist70
        },
        label = "icon tint color",
    )
    val textColor by animateColorAsState(
        targetValue = when {
            !enabled || value.text.isEmpty() -> GmaylTheme.color.mist50
            else -> GmaylTheme.color.mist100
        },
        label = "text color",
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = GmaylTheme.color.mist30,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = value.text.ifEmpty { hint.orEmpty() },
            style = GmaylTheme.typography.contentMediumRegular,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .clickable(enabled = enabled && value.text.isNotEmpty(), onClick = onClick)
                .padding(vertical = 4.dp)
                .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = "icon copy",
                tint = iconColor,
            )
        }
    }
}
