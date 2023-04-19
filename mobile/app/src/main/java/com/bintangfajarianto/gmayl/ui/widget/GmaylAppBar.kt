package com.bintangfajarianto.gmayl.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylAppBar(
    @DrawableRes navigationIcon: Int,
    onClickNavigationIcon: () -> Unit,
    title: String?,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            GmaylNavigationIcon(
                // Internally, AppBar has a 4.dp padding, since the design requires 16.dp padding, add another 12.dp here instead
                modifier = Modifier.padding(horizontal = 12.dp),
                navigationIcon = navigationIcon,
                onClick = onClickNavigationIcon,
            )
        },
        title = {
            if (title != null) {
                Text(
                    text = title,
                    style = GmaylTheme.typography.titleMediumBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = if (title != null) Color.White else Color.Transparent,
            scrolledContainerColor = if (title != null) Color.White else Color.Transparent,
            navigationIconContentColor = GmaylTheme.color.mist100,
            titleContentColor = GmaylTheme.color.mist100,
            actionIconContentColor = GmaylTheme.color.mist100,
        ),
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

@Composable
private fun GmaylNavigationIcon(
    navigationIcon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape = CircleShape)
            .background(color = Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = navigationIcon),
            tint = GmaylTheme.color.mist100,
            contentDescription = "navigation icon",
        )
    }
}

@Preview
@Composable
private fun GmaylNavigationIconPreview() {
    GmaylTheme {
        GmaylNavigationIcon(navigationIcon = R.drawable.ic_arrow_left, onClick = { })
    }
}
