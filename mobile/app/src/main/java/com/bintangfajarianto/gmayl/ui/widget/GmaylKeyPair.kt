package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylKeypair(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = GmaylTheme.typography.contentMediumRegular,
            color = GmaylTheme.color.mist70,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(modifier = Modifier.weight(1f)) {
            content(this)
        }
    }
}

private class GmaylKeypairParamProvider :
    CollectionPreviewParameterProvider<@Composable BoxScope.() -> Unit>(
        listOf(
            @Composable {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GmaylTheme.color.mist30),
                )
            },
            @Composable {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Info",
                    style = GmaylTheme.typography.contentMediumBold,
                    color = GmaylTheme.color.mist100,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                )
            },
        ),
    )

@Preview
@Composable
private fun GmaylKeypairPreview(
    @PreviewParameter(GmaylKeypairParamProvider::class) content: @Composable BoxScope.() -> Unit,
) {
    GmaylTheme {
        GmaylKeypair(label = "Label", content = content)
    }
}
