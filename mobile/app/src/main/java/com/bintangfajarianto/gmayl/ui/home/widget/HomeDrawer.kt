package com.bintangfajarianto.gmayl.ui.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.auth.UserItem
import com.bintangfajarianto.gmayl.data.auth.UserItemResponse
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun HomeDrawer(
    navController: NavController,
    userItem: UserItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(200.dp)
            .background(color = GmaylTheme.color.mist10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AsyncImage(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(userItem.avatarUrl)
                .crossfade(true)
                .build(),
            placeholder = ColorPainter(color = GmaylTheme.color.mist50),
            fallback = ColorPainter(color = GmaylTheme.color.mist50),
            error = ColorPainter(color = GmaylTheme.color.mist50),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userItem.name,
            style = GmaylTheme.typography.contentMediumBold,
            color = GmaylTheme.color.mist100,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = userItem.email,
            style = GmaylTheme.typography.contentSmallRegular,
            color = GmaylTheme.color.mist70,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(24.dp))
        DrawerItem(
            drawerItemIcon = R.drawable.ic_inbox,
            drawerItemName = "Inbox",
            onClickItem = {},
        )
        DrawerItem(
            drawerItemIcon = R.drawable.ic_send,
            drawerItemName = "Sent",
            onClickItem = {},
        )
    }
}

@Preview
@Composable
fun PreviewHomeDrawer() {
    HomeDrawer(
        navController = rememberNavController(),
        userItem = UserItemResponse(
            name = "Bintang Fajarianto",
            email = "13519138@std.stei.itb.ac.id",
            avatarUrl = "",
        ),
    )
}
