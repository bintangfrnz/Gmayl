package com.bintangfajarianto.gmayl.utils

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.HtmlCompat

fun parseHtmlText(htmlText: String): AnnotatedString =
    HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toAnnotatedString()

private fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    append(this@toAnnotatedString.toString())
    getSpans(0, this@toAnnotatedString.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)

        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold),
                    start = start,
                    end = end,
                )
                Typeface.ITALIC -> addStyle(
                    style = SpanStyle(fontStyle = FontStyle.Italic),
                    start = start,
                    end = end,
                )
                Typeface.BOLD_ITALIC -> addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                    start = start,
                    end = end,
                )
            }
            is UnderlineSpan -> addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = start,
                end = end,
            )
        }
    }
}