package com.bintangfajarianto.gmayl.ui.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun HomeDrawer(
    user: User,
    drawerItems: ImmutableList<DrawerItemType>,
    selectedDrawerItem: DrawerItemType,
    onChangeSelectedDrawerItem: (DrawerItemType) -> Unit,
    onClickKeyGenerator: () -> Unit,
    onClickLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .width(200.dp)
            .background(color = GmaylTheme.color.mist10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_default_account),
                fallback = painterResource(id = R.drawable.ic_default_account),
                error = painterResource(id = R.drawable.ic_default_account),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = user.name,
                style = GmaylTheme.typography.contentMediumBold,
                color = GmaylTheme.color.mist100,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.email,
                style = GmaylTheme.typography.contentSmallRegular,
                color = GmaylTheme.color.mist70,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = GmaylTheme.color.mist30)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(items = drawerItems) { drawerItem ->
            when (drawerItem) {
                DrawerItemType.KEY -> DrawerItem(
                    drawerItemIcon = drawerItem.iconId,
                    drawerItemName = drawerItem.title,
                    color = GmaylTheme.color.primary70,
                    onClickItem = onClickKeyGenerator,
                )
                DrawerItemType.LOGOUT -> DrawerItem(
                    drawerItemIcon = drawerItem.iconId,
                    drawerItemName = drawerItem.title,
                    color = GmaylTheme.color.rose50,
                    onClickItem = onClickLogout,
                )
                else -> DrawerItem(
                    drawerItemIcon = drawerItem.iconId,
                    drawerItemName = drawerItem.title,
                    selected = selectedDrawerItem == drawerItem,
                    onClickItem = { onChangeSelectedDrawerItem(drawerItem) },
                )
            }
            Divider(color = GmaylTheme.color.mist10)
        }
    }
}

@Preview
@Composable
private fun PreviewHomeDrawer() {

    var selectedDrawerItem by remember {
        mutableStateOf(value = DrawerItemType.INBOX)
    }

    HomeDrawer(
        user = User(
            name = "Bintang Fajarianto",
            email = "13519138@std.stei.itb.ac.id",
            avatarUrl = "",
        ),
        drawerItems = enumValues<DrawerItemType>().toList().toPersistentList(),
        selectedDrawerItem = selectedDrawerItem,
        onChangeSelectedDrawerItem = { selectedDrawerItem = it},
        onClickKeyGenerator = {},
        onClickLogout = {},
    )
}
