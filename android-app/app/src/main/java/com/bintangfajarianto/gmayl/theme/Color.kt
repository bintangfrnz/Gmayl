package com.bintangfajarianto.gmayl.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Immutable
class GmaylColor(
    /** Brand Color **/

    // Primary
    val primary10: Color = Color(0xFFE8F6FF),
    val primary30: Color = Color(0xFF9BD9FF),
    val primary50: Color = Color(0xFF0091EA),
    val primary70: Color = Color(0xFF006FB3),
    val primary90: Color = Color(0xFF014A78),

    // Secondary
    val autumn10: Color = Color(0xFFF5D1BD),
    val autumn30: Color = Color(0xFFEEAF8C),
    val autumn50: Color = Color(0xFFE78D5B),
    val autumn70: Color = Color(0xFFB97149),
    val autumn90: Color = Color(0xFF8B5537),

    /** State Color **/

    // Positive
    val cactus10: Color = Color(0xFFDFFDF0),
    val cactus30: Color = Color(0xFFA8F0CD),
    val cactus50: Color = Color(0xFF21BF73),
    val cactus70: Color = Color(0xFF139859),
    val cactus90: Color = Color(0xFF117041),

    // Warn
    val sunflower10: Color = Color(0xFFFDE9A0),
    val sunflower30: Color = Color(0xFFFCD859),
    val sunflower50: Color = Color(0xFFFBC712),
    val sunflower70: Color = Color(0xFFC99F0E),
    val sunflower90: Color = Color(0xFF97770B),

    // Negative
    val rose10: Color = Color(0xFFFFF1EC),
    val rose30: Color = Color(0xFFFFAD98),
    val rose50: Color = Color(0xFFDD2C00),
    val rose70: Color = Color(0xFFBC2701),
    val rose90: Color = Color(0xFF7B1A02),

    /** Black and White Color **/

    val mist10: Color = Color(0xFFFFFFFF),
    val mist20: Color = Color(0xFFF6F5F4),
    val mist30: Color = Color(0xFFE3E2DF),
    val mist40: Color = Color(0xFFC8C6C0),
    val mist50: Color = Color(0xFFA6A39D),
    val mist60: Color = Color(0xFF5A5852),
    val mist70: Color = Color(0xFF43423C),
    val mist80: Color = Color(0xFF302F2D),
    val mist90: Color = Color(0xFF22211F),
    val mist100: Color = Color(0xFF050504),
)

internal val LocalGmaylColor = staticCompositionLocalOf { GmaylColor() }

@Composable
private fun BoxSampleColor(
    label: String,
    isDark: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = GmaylTheme.typography.titleMediumBold,
            color = if (isDark) GmaylTheme.color.mist10 else GmaylTheme.color.mist100,
        )
    }
}

@Preview
@Composable
private fun PreviewGmaylColorPrimary() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "primary10", isDark = false, color = GmaylTheme.color.primary10)
            BoxSampleColor(label = "primary30", isDark = false, color = GmaylTheme.color.primary30)
            BoxSampleColor(label = "primary50", isDark = false, color = GmaylTheme.color.primary50)
            BoxSampleColor(label = "primary70", isDark = true, color = GmaylTheme.color.primary70)
            BoxSampleColor(label = "primary90", isDark = true, color = GmaylTheme.color.primary90)
        }
    }
}

@Preview
@Composable
private fun PreviewGmaylColorSecondary() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "autumn10", isDark = false, color = GmaylTheme.color.autumn10)
            BoxSampleColor(label = "autumn30", isDark = false, color = GmaylTheme.color.autumn30)
            BoxSampleColor(label = "autumn50", isDark = false, color = GmaylTheme.color.autumn50)
            BoxSampleColor(label = "autumn70", isDark = true, color = GmaylTheme.color.autumn70)
            BoxSampleColor(label = "autumn90", isDark = true, color = GmaylTheme.color.autumn90)
        }
    }
}

@Preview
@Composable
private fun PreviewGmaylColorSuccess() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "cactus10", isDark = false, color = GmaylTheme.color.cactus10)
            BoxSampleColor(label = "cactus30", isDark = false, color = GmaylTheme.color.cactus30)
            BoxSampleColor(label = "cactus50", isDark = false, color = GmaylTheme.color.cactus50)
            BoxSampleColor(label = "cactus70", isDark = true, color = GmaylTheme.color.cactus70)
            BoxSampleColor(label = "cactus90", isDark = true, color = GmaylTheme.color.cactus90)
        }
    }
}

@Preview
@Composable
private fun PreviewGmaylColorWarning() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "sunflower10", isDark = false, color = GmaylTheme.color.sunflower10)
            BoxSampleColor(label = "sunflower30", isDark = false, color = GmaylTheme.color.sunflower30)
            BoxSampleColor(label = "sunflower50", isDark = false, color = GmaylTheme.color.sunflower50)
            BoxSampleColor(label = "sunflower70", isDark = true, color = GmaylTheme.color.sunflower70)
            BoxSampleColor(label = "sunflower90", isDark = true, color = GmaylTheme.color.sunflower90)
        }
    }
}

@Preview
@Composable
private fun PreviewGmaylColorError() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "rose10", isDark = false, color = GmaylTheme.color.rose10)
            BoxSampleColor(label = "rose30", isDark = false, color = GmaylTheme.color.rose30)
            BoxSampleColor(label = "rose50", isDark = false, color = GmaylTheme.color.rose50)
            BoxSampleColor(label = "rose70", isDark = true, color = GmaylTheme.color.rose70)
            BoxSampleColor(label = "rose90", isDark = true, color = GmaylTheme.color.rose90)
        }
    }
}

@Preview
@Composable
private fun PreviewGmaylColorBW() {
    GmaylTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            BoxSampleColor(label = "mist10", isDark = false, color = GmaylTheme.color.mist10)
            BoxSampleColor(label = "mist20", isDark = false, color = GmaylTheme.color.mist20)
            BoxSampleColor(label = "mist30", isDark = false, color = GmaylTheme.color.mist30)
            BoxSampleColor(label = "mist40", isDark = false, color = GmaylTheme.color.mist40)
            BoxSampleColor(label = "mist50", isDark = false, color = GmaylTheme.color.mist50)
            BoxSampleColor(label = "mist60", isDark = true, color = GmaylTheme.color.mist60)
            BoxSampleColor(label = "mist70", isDark = true, color = GmaylTheme.color.mist70)
            BoxSampleColor(label = "mist80", isDark = true, color = GmaylTheme.color.mist80)
            BoxSampleColor(label = "mist90", isDark = true, color = GmaylTheme.color.mist90)
            BoxSampleColor(label = "mist100", isDark = true, color = GmaylTheme.color.mist100)
        }
    }
}
