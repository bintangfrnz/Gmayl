package com.bintangfajarianto.gmayl.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bintangfajarianto.gmayl.R

@Immutable
class GmaylTypography(
    val titleLargeBold: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = openSansFamily,
        lineHeight = 36.sp,
    ),

    val titleMediumBold: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = openSansFamily,
        lineHeight = 28.sp,
    ),

    val titleSmallSemiBold: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = openSansFamily,
        lineHeight = 24.sp,
    ),

    val titleSmallRegular: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = openSansFamily,
        lineHeight = 24.sp,
    ),

    val contentMediumBold: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = openSansFamily,
        lineHeight = 22.sp,
    ),

    val contentMediumRegular: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = openSansFamily,
        lineHeight = 22.sp,
    ),

    val contentSmallSemiBold: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = openSansFamily,
        lineHeight = 18.sp,
    ),

    val contentSmallRegular: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = openSansFamily,
        lineHeight = 18.sp,
    ),
)

internal val openSansFamily = FontFamily(
    Font(resId = R.font.opensans_light, style = FontStyle.Normal, weight = FontWeight.Light),
    Font(resId = R.font.opensans_regular, style = FontStyle.Normal, weight = FontWeight.Normal),
    Font(resId = R.font.opensans_medium, style = FontStyle.Normal, weight = FontWeight.Medium),
    Font(resId = R.font.opensans_semibold, style = FontStyle.Normal, weight = FontWeight.SemiBold),
    Font(resId = R.font.opensans_bold, style = FontStyle.Normal, weight = FontWeight.Bold),
    Font(resId = R.font.opensans_extrabold, style = FontStyle.Normal, weight = FontWeight.ExtraBold),
)

internal val LocalGmaylTypography = staticCompositionLocalOf { GmaylTypography() }

@Preview(showBackground = true)
@Composable
private fun PreviewGmaylTypographyTitle() {
    GmaylTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "This is Title - Large Bold Text", style = GmaylTheme.typography.titleLargeBold)
            Text(text = "This is Title - Medium Bold Text", style = GmaylTheme.typography.titleMediumBold)
            Text(text = "This is Title - Small SemiBold Text", style = GmaylTheme.typography.titleSmallSemiBold)
            Text(text = "This is Title - Small Regular Text", style = GmaylTheme.typography.titleSmallRegular)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGmaylTypographyContent() {
    GmaylTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "This is Content - Medium Bold Text", style = GmaylTheme.typography.contentMediumBold)
            Text(text = "This is Content - Medium Regular Text", style = GmaylTheme.typography.contentMediumRegular)
            Text(text = "This is Content - Small SemiBold Text", style = GmaylTheme.typography.contentSmallSemiBold)
            Text(text = "This is Content - Small Regular Text", style = GmaylTheme.typography.contentSmallRegular)
        }
    }
}
