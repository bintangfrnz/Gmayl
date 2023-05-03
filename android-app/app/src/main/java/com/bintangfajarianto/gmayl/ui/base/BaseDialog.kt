package com.bintangfajarianto.gmayl.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.general.DialogImageType
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.utils.parseHtmlText
import com.bintangfajarianto.gmayl.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.widget.GmaylSecondaryButton

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    data: DialogData? = DialogData(),
    isLoading: Boolean = false,
    onClickNegative: (() -> Unit)? = null,
    onClickPositive: () -> Unit,
) {
    Column(
        modifier = modifier.background(color = GmaylTheme.color.mist10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        HandlerBar()
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                modifier = Modifier.size(56.dp),
                painter = painterResource(getImageResource(type = data?.imageType)),
                contentDescription = "dialog image",
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val titleText = data?.titleText.orEmpty()
                Text(
                    text = titleText,
                    style = GmaylTheme.typography.titleMediumBold,
                    color = GmaylTheme.color.mist100,
                )
                val descriptionText = data?.descriptionText.orEmpty()
                Text(
                    text = parseHtmlText(descriptionText),
                    style = GmaylTheme.typography.contentMediumRegular,
                    color = GmaylTheme.color.mist70,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val negativeButtonText = data?.negativeButtonText.orEmpty()
            if (negativeButtonText.isNotEmpty()) {
                GmaylSecondaryButton(
                    modifier = Modifier.weight(1f),
                    label = negativeButtonText,
                    onClick = { onClickNegative?.invoke() },
                )
            }
            val positiveButtonText = data?.positiveButtonText.orEmpty()
            GmaylPrimaryButton(
                modifier = Modifier.weight(1f),
                label = positiveButtonText,
                loading = isLoading,
                onClick = onClickPositive,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Stable
private fun getImageResource(type: DialogImageType?): Int =
    when (type) {
        DialogImageType.WARNING -> R.drawable.ic_warning
        DialogImageType.QUESTION -> R.drawable.ic_question
        DialogImageType.DEFAULT -> R.drawable.ic_default
        null -> R.drawable.ic_default
    }

private class BaseDialogParameterProvider : CollectionPreviewParameterProvider<DialogData>(
    listOf(
        DialogData(
            imageType = DialogImageType.DEFAULT,
            titleText = "This is title",
            descriptionText = "This is description",
            positiveButtonText = "Positive",
        ),
        DialogData(
            imageType = DialogImageType.QUESTION,
            titleText = "This is title",
            descriptionText = "This is <b>description</b>",
            positiveButtonText = "Positive",
            negativeButtonText = "Negative",
        ),
    )
)

@Preview
@Composable
private fun PreviewBaseDialog(
    @PreviewParameter(BaseDialogParameterProvider::class) dialogData: DialogData,
) {
    BaseDialog(
        data = dialogData,
        onClickNegative = {},
        onClickPositive = {},
    )
}

@Composable
private fun HandlerBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(6.dp)
            .width(42.dp)
            .clip(CircleShape)
            .background(GmaylTheme.color.mist70),
    )
}

@Preview
@Composable
private fun PreviewHandlerBar() {
    Box(
        modifier = Modifier.size(width = 100.dp, height = 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        HandlerBar()
    }
}
