package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylPrimaryButton(
    label: String,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = GmaylTheme.color.primary50,
            contentColor = Color.White,
            disabledContainerColor = GmaylTheme.color.mist40,
            disabledContentColor = Color.White,
        ),
    ) {
        GmaylButtonContent(
            label = label,
            loading = loading,
            progressIndicatorColor = Color.White,
        )
    }
}

@Composable
private fun GmaylButtonContent(
    label: String,
    loading: Boolean,
    progressIndicatorColor: Color,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
) {
    Crossfade(modifier = modifier, targetState = loading, label = "button content") {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            if (it) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 3.dp,
                    color = progressIndicatorColor,
                )
            } else {
                Text(
                    text = label,
                    style = GmaylTheme.typography.titleSmallRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                )
            }
        }
    }
}

private class GmaylButtonParamProvider :
    CollectionPreviewParameterProvider<GmaylButtonParamProvider.Param>(
        listOf(
            Param(label = "Button", loading = false, enabled = true),
            Param(label = "Button", loading = true, enabled = true),
            Param(label = "Disabled Button", loading = false, enabled = false),
        ),
    ) {
    data class Param(
        val label: String,
        val loading: Boolean,
        val enabled: Boolean,
    )
}

@Preview
@Composable
private fun GmaylPrimaryButtonPreview(
    @PreviewParameter(GmaylButtonParamProvider::class) param: GmaylButtonParamProvider.Param,
) {
    GmaylTheme {
        GmaylPrimaryButton(
            label = param.label,
            loading = param.loading,
            enabled = param.enabled,
            onClick = { },
        )
    }
}
