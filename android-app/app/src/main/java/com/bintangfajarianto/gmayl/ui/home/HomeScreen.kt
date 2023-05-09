package com.bintangfajarianto.gmayl.ui.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.core.navigation.HomeRoutes
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.general.AlertType
import com.bintangfajarianto.gmayl.data.model.general.DataCondition
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.extension.GetResult
import com.bintangfajarianto.gmayl.feature.vm.home.HomeAction
import com.bintangfajarianto.gmayl.feature.vm.home.HomeViewModel
import com.bintangfajarianto.gmayl.feature.vm.home.HomeViewState
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.base.BaseDialog
import com.bintangfajarianto.gmayl.ui.home.widget.HomeDrawer
import com.bintangfajarianto.gmayl.ui.home.widget.MessageItem
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarHost
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarVisuals
import com.bintangfajarianto.gmayl.ui.widget.gmaylShimmerBrush
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberViewModel

@Composable
fun HomeRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel by rememberViewModel()
    val viewState by viewModel.stateFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var user by remember {
        mutableStateOf(value = User())
    }

    navController.GetResult<DataMessageCondition>(
        key = HomeRoutes.HOME_ARG,
        onResult = {
            viewModel.onAction(HomeAction.InitData(it))
        },
    )

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            user = viewModel.getUser()
        }
    }

    LaunchedEffect(Unit) {
        if (!viewState.isInit) {
            viewModel.onAction(HomeAction.InitData(null))
        }
    }

    HomeScreen(
        modifier = modifier,
        viewState = viewState,
        user = user,
        drawerItems = enumValues<DrawerItemType>().toList().toPersistentList(),
        onSelectDrawerItem = { viewModel.onAction(HomeAction.OnSelectDrawerItem(it)) },
        onClickLogout = { viewModel.onAction(HomeAction.OnClickLogout) },
        onClickMailItem = {
            viewModel.onAction(
                HomeAction.OnClickMailItem(mail = it, mailType = viewState.selectedDrawerItem)
            )
        },
        onClickSendMail = { viewModel.onAction(HomeAction.OnClickSendMail(user)) },
        onCloseApplication = {
            val activity = (context as? Activity)
            activity?.finish()
        },
        onDismissDialog = { viewModel.onAction(HomeAction.OnDismissDialog) },
        onDismissSnackBar = { viewModel.onAction(HomeAction.OnDismissSnackBar) },
    )

    BackHandler(enabled = true) {
        viewModel.onAction(HomeAction.OnClickBack)
    }
}

@Composable
private fun HomeScreen(
    viewState: HomeViewState,
    user: User,
    drawerItems: ImmutableList<DrawerItemType>,
    onClickLogout: () -> Unit,
    onClickMailItem: (Mail) -> Unit,
    onClickSendMail: () -> Unit,
    onCloseApplication: () -> Unit,
    onDismissDialog: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onSelectDrawerItem: (DrawerItemType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewState.dataMsgCondition, key2 = viewState.loading) {
        if (!viewState.loading) {
            val dataCondition = viewState.dataMsgCondition?.dataCondition ?: return@LaunchedEffect
            val success = dataCondition == DataCondition.Success

            snackBarHostState.showSnackbar(
                GmaylSnackBarVisuals(
                    message = viewState.dataMsgCondition.message,
                    alertType = when (success) {
                        true -> AlertType.Positive
                        else -> AlertType.Negative
                    },
                ),
            )

            onDismissSnackBar()
        }
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(
                user = user,
                drawerItems = drawerItems,
                selectedDrawerItem = viewState.selectedDrawerItem,
                onChangeSelectedDrawerItem = onSelectDrawerItem,
                onClickLogout = onClickLogout,
            )
        },
    ) {
        val coroutineScope = rememberCoroutineScope()

        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
        )

        LaunchedEffect(key1 = sheetState.currentValue) {
            if (sheetState.currentValue == ModalBottomSheetValue.Hidden && !viewState.loading) {
                onDismissDialog()
            }
        }

        LaunchedEffect(key1 = viewState.dialogData) {
            if (viewState.dialogData != null) {
                sheetState.show()
            }
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = GmaylTheme.color.mist10,
            sheetContent = {
                when {
                    viewState.dialogData != null -> BaseDialog(
                        data = viewState.dialogData,
                        onClickPositive = {
                            coroutineScope.launch { sheetState.hide() }
                            onCloseApplication()
                        },
                        onClickNegative = {
                            coroutineScope.launch { sheetState.hide() }
                        }
                    )
                    else -> Box(modifier = Modifier.size(1.dp))
                }
            }
        ) {
            Scaffold(
                snackbarHost = { GmaylSnackBarHost(hostState = snackBarHostState) },
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
                    if (!viewState.loading && viewState.dataMsgCondition == null) {
                        FloatingActionButton(
                            shape = CircleShape,
                            containerColor = GmaylTheme.color.primary50,
                            contentColor = GmaylTheme.color.mist10,
                            onClick = onClickSendMail,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pencil),
                                contentDescription = "Compose",
                            )
                        }
                    }
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = GmaylTheme.color.mist10)
                        .padding(innerPadding),
                ) {
                    when {
                        viewState.loading -> HomeShimmer()
                        viewState.mailItems.isEmpty() -> Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(
                                id = R.string.home_empty_mail,
                                viewState.selectedDrawerItem.title,
                            ),
                            style = GmaylTheme.typography.titleSmallSemiBold,
                        )
                        else -> HomeScreenContent(
                            mails = viewState.mailItems,
                            onClickMailItem = onClickMailItem,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeShimmer(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MessageItemShimmer()
        MessageItemShimmer()
        MessageItemShimmer()
    }
}

@Composable
private fun MessageItemShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(color = GmaylTheme.color.mist10)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(brush = gmaylShimmerBrush(), shape = CircleShape),
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(12.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(brush = gmaylShimmerBrush()),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(brush = gmaylShimmerBrush()),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(12.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(brush = gmaylShimmerBrush()),
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    mails: List<Mail>,
    modifier: Modifier = Modifier,
    onClickMailItem: (Mail) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(mails) { mail ->
            MessageItem(
                mail = mail,
                onClickItem = { onClickMailItem(mail) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeShimmer() {
    HomeShimmer()
}

private class HomeScreenParameterProvider :
    CollectionPreviewParameterProvider<HomeViewState>(
        listOf(
            HomeViewState(loading = true),
            HomeViewState(selectedDrawerItem = DrawerItemType.INBOX),
            HomeViewState(selectedDrawerItem = DrawerItemType.SENT),
            HomeViewState(
                selectedDrawerItem = DrawerItemType.INBOX,
                mailItems = listOf(
                    InboxMail(
                        sender = User("Bintang F."),
                        receiver = User(),
                        subject = "[Tugas 1] Ini contoh subject aja",
                        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
                        sentTime = "2023-03-05T13:15:30+07:00",
                        encrypted = false,
                    ),
                    InboxMail(
                        sender = User("Bintang Fajar"),
                        receiver = User(),
                        subject = "[Tugas 2] Ini contoh subject aja",
                        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
                        sentTime = "2023-04-05T13:15:30+07:00",
                        encrypted = false,
                    ),
                    InboxMail(
                        sender = User("Bintang Fajarianto"),
                        receiver = User(),
                        subject = "[Tugas 3] Ini contoh subject aja",
                        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
                        sentTime = "2023-05-05T13:15:30+07:00",
                        encrypted = false,
                    ),
                ),
            ),
            HomeViewState(
                selectedDrawerItem = DrawerItemType.SENT,
                mailItems = listOf(
                    SentMail(
                        sender = User("Admin"),
                        receiver = User(),
                        subject = "[Tugas 1] Ini contoh subject aja",
                        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
                        sentTime = "2023-03-05T13:15:30+07:00",
                        encrypted = false,
                    ),
                    SentMail(
                        sender = User("Admin"),
                        receiver = User(),
                        subject = "[Tugas 2] Ini contoh subject aja",
                        body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
                        sentTime = "2023-04-05T13:15:30+07:00",
                        encrypted = false,
                    ),
                ),
            ),
        ),
    )

@Preview
@Composable
private fun PreviewHomeScreen(
    @PreviewParameter(HomeScreenParameterProvider::class) viewState: HomeViewState,
) {
    HomeScreen(
        viewState = viewState,
        user = User(
            name = "Admin",
            email = "admin@gmail.com",
        ),
        drawerItems = enumValues<DrawerItemType>().toList().toPersistentList(),
        onClickLogout = {},
        onClickMailItem = {},
        onClickSendMail = {},
        onCloseApplication = {},
        onDismissDialog = {},
        onDismissSnackBar = {},
        onSelectDrawerItem = {},
    )
}
