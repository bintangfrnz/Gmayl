package com.bintangfajarianto.gmayl.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.auth.UserItem
import com.bintangfajarianto.gmayl.data.auth.UserItemResponse
import com.bintangfajarianto.gmayl.data.home.MessageItem
import com.bintangfajarianto.gmayl.data.home.MessageItemResponse
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.home.widget.HomeDrawer
import com.bintangfajarianto.gmayl.ui.home.widget.MessageItem
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.ShimmerListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun HomeRoute() {

}

@Composable
private fun HomeScreen(
    navController: NavController,
    userItem: UserItem,
    listMessage: ImmutableList<MessageItem>,
    loading: Boolean,
    onClickItem: (MessageItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(
                navController = navController,
                userItem = userItem,
            )
        },
    ) {
        Scaffold(
            topBar = {
                GmaylAppBar(
                    navigationIcon = R.drawable.ic_hamburger,
                    onClickNavigationIcon = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    title = stringResource(id = R.string.app_name),
                )
            },
            floatingActionButton = {
                if (!loading) {
                    FloatingActionButton(
                        shape = CircleShape,
                        containerColor = GmaylTheme.color.primary50,
                        contentColor = GmaylTheme.color.mist10,
                        onClick = {},
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pencil),
                            contentDescription = "fab",
                        )
                    }
                }
            },

            ) {
            HomeScreenContent(
                modifier = Modifier.padding(it),
                listMessage = listMessage,
                loading = loading,
                onClickItem = onClickItem,
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    listMessage: ImmutableList<MessageItem>,
    loading: Boolean,
    onClickItem: (MessageItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LazyColumn(modifier = modifier, state = listState) {
        if (loading) {
            items(3) {
                ShimmerListItem()
            }
        } else {
            items(listMessage) { messageItem ->
                MessageItem(
                    messageItem = messageItem,
                    onClickItem = {
                        onClickItem(messageItem)
                    },
                )
            }
        }
    }
}

private class HomeScreenParameterProvider : CollectionPreviewParameterProvider<Boolean>(
    listOf(true, false)
)

@Preview
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(HomeScreenParameterProvider::class) loading: Boolean,
) {
    val mockListMessage: ImmutableList<MessageItem> = persistentListOf(
        MessageItemResponse(
            sender = "Bintang F.",
            subject = "[Tugas 1] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-03-05T13:15:30+07:00",
            imageUrl = "",
        ),
        MessageItemResponse(
            sender = "Bintang Fajar",
            subject = "[Tugas 2] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-04-05T13:15:30+07:00",
            imageUrl = "",
        ),
        MessageItemResponse(
            sender = "Bintang Fajarianto",
            subject = "[Tugas 3] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-05-05T13:15:30+07:00",
            imageUrl = "",
        )
    )

    HomeScreen(
        navController = rememberNavController(),
        userItem = UserItemResponse(
            name = "Bintang Fajarianto",
            email = "13519138@std.stei.itb.ac.id",
            avatarUrl = "",
        ),
        listMessage = mockListMessage,
        loading = loading,
        onClickItem = {},
    )
}
