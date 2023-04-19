package com.bintangfajarianto.gmayl.ui.home.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun DrawerItem(
    @DrawableRes drawerItemIcon: Int,
    drawerItemName: String,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .fillMaxWidth()
            .background(color = GmaylTheme.color.mist10)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = drawerItemIcon),
            contentDescription = "drawer icon",
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = drawerItemName,
            style = GmaylTheme.typography.contentMediumBold,
            color = GmaylTheme.color.mist100,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun PreviewDrawerItem() {
    DrawerItem(
        drawerItemIcon = R.drawable.ic_inbox,
        drawerItemName = "Inbox",
    ) {}
}
