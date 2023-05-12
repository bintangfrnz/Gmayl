package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylClickableText(
    text: String,
    clickableText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = GmaylTheme.typography.contentMediumRegular,
    onClickSpecificText: () -> Unit,
) {
    val annotatedText = buildAnnotatedString {
        append(text)

        val startIndex = text.indexOf(clickableText)
        val endIndex = startIndex + clickableText.length

        addStyle(
            style = SpanStyle(color = GmaylTheme.color.primary50, fontWeight = FontWeight.ExtraBold),
            start = startIndex,
            end = endIndex,
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "",
            start = startIndex,
            end = endIndex,
        )
    }

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        style = style,
        onClick = {
            annotatedText.getStringAnnotations("URL", it, it).firstOrNull()
                ?.let { onClickSpecificText() }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewGmaylClickableText() {
    GmaylClickableText(
        text = stringResource(id = R.string.send_mail_check_private_key),
        clickableText = stringResource(id = R.string.send_mail_here),
    ) { }
}
