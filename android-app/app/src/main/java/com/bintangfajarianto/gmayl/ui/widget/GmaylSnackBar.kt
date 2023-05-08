package com.bintangfajarianto.gmayl.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.general.AlertType
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylSnackBar(
    message: String,
    modifier: Modifier = Modifier,
    alertType: AlertType = AlertType.Positive,
) {
    val state = GmaylSnackBarState(
        positiveBackgroundColor = GmaylTheme.color.cactus50,
        negativeBackgroundColor = GmaylTheme.color.rose50,
        positiveImage = R.drawable.vector_snackbar_positive,
        negativeImage = R.drawable.vector_snackbar_negative,
    )

    val backgroundColor = state.backgroundColor(alertType = alertType)
    val image = state.image(alertType = alertType)

    Box(
        modifier = modifier
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Image(
            modifier = Modifier.align(Alignment.TopStart),
            painter = painterResource(id = image.value),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 42.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            text = message,
            color = GmaylTheme.color.mist10,
            style = GmaylTheme.typography.contentMediumBold,
        )
    }
}

@Composable
fun GmaylSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(modifier = modifier, hostState = hostState) {
        val gmaylSnackBarVisuals = it.visuals as GmaylSnackBarVisuals

        GmaylSnackBar(
            modifier = Modifier.padding(12.dp),
            message = gmaylSnackBarVisuals.message,
            alertType = gmaylSnackBarVisuals.alertType,
        )
    }
}

@Stable
data class GmaylSnackBarVisuals(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val alertType: AlertType = AlertType.Positive,
) : SnackbarVisuals {
    override val actionLabel: String?
        get() = null
    override val withDismissAction: Boolean
        get() = false
}

@Immutable
private class GmaylSnackBarState constructor(
    private val positiveBackgroundColor: Color,
    private val negativeBackgroundColor: Color,
    @DrawableRes private val positiveImage: Int,
    @DrawableRes private val negativeImage: Int,
) {
    @Composable
    fun backgroundColor(alertType: AlertType): State<Color> {
        val target = when (alertType) {
            AlertType.Positive -> positiveBackgroundColor
            else -> negativeBackgroundColor
        }

        return animateColorAsState(target, tween(durationMillis = 100))
    }

    @Composable
    fun image(alertType: AlertType): State<Int> {
        val target = when (alertType) {
            AlertType.Positive -> positiveImage
            else -> negativeImage
        }

        return animateIntAsState(target, tween(durationMillis = 100))
    }
}

private class GmaylSnackBarParamProvider :
    CollectionPreviewParameterProvider<GmaylSnackBarParamProvider.Param>(
        listOf(
            Param("Put your message here (positive)", AlertType.Positive),
            Param("Put your message here (negative)", AlertType.Negative),
        ),
    ) {
    data class Param(
        val message: String,
        val alertType: AlertType,
    )
}

@Preview
@Composable
private fun PreviewGmaylSnackBar(
    @PreviewParameter(GmaylSnackBarParamProvider::class) param: GmaylSnackBarParamProvider.Param,
) {
    GmaylSnackBar(message = param.message, alertType = param.alertType)
}