package com.bintangfajarianto.gmayl.ui.home.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun DrawerItem(
    @DrawableRes drawerItemIcon: Int,
    drawerItemName: String,
    modifier: Modifier = Modifier,
    color: Color = GmaylTheme.color.mist100,
    selected: Boolean = false,
    onClickItem: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .background(color = GmaylTheme.color.mist10)
            .padding(horizontal = 8.dp),
    ) {
        val background = when {
            selected -> Modifier.background(color = GmaylTheme.color.primary30, shape = CircleShape)
            else -> Modifier
        }

        Row(
            modifier = modifier
                .clickable(onClick = onClickItem)
                .fillMaxWidth()
                .then(background)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = drawerItemIcon),
                contentDescription = "drawer icon",
                tint = color,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = drawerItemName,
                style = GmaylTheme.typography.contentSmallSemiBold,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private class DrawerItemParameterProvider :
    CollectionPreviewParameterProvider<DrawerItemParameterProvider.DrawerItemParam>(
        listOf(
            DrawerItemParam(
                drawerItem = DrawerItemType.INBOX,
                selected = false,
            ),
            DrawerItemParam(
                drawerItem = DrawerItemType.SENT,
                selected = true,
            ),
            DrawerItemParam(
                drawerItem = DrawerItemType.LOGOUT,
                selected = false,
            ),
        ),
    ) {
        data class DrawerItemParam(
            val drawerItem: DrawerItemType,
            val selected: Boolean,
        )
    }

@Preview
@Composable
private fun PreviewDrawerItem(
    @PreviewParameter(DrawerItemParameterProvider::class) param: DrawerItemParameterProvider.DrawerItemParam,
) {
    val color = when (param.drawerItem) {
        DrawerItemType.LOGOUT -> GmaylTheme.color.rose50
        else -> GmaylTheme.color.mist100
    }
    DrawerItem(
        drawerItemIcon = param.drawerItem.iconId,
        drawerItemName = param.drawerItem.title,
        color = color,
        selected = param.selected,
    ) {}
}
