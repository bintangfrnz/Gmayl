package com.bintangfajarianto.gmayl.ui.home.detailmail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.core.navigation.HomeRoutes
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.general.DataCondition
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.feature.vm.home.detailmail.MailDetailAction
import com.bintangfajarianto.gmayl.feature.vm.home.detailmail.MailDetailViewModel
import com.bintangfajarianto.gmayl.feature.vm.home.detailmail.MailDetailViewState
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.base.BaseDialog
import com.bintangfajarianto.gmayl.ui.home.detailmail.widget.SenderItem
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import org.kodein.di.compose.rememberViewModel

@Composable
fun MailDetailRoute(
    mail: Mail,
    mailType: DrawerItemType,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: MailDetailViewModel by rememberViewModel()
    val viewState by viewModel.stateFlow.collectAsState()

    MailDetailScreen(
        modifier = modifier,
        viewState = viewState,
        mail = mail,
        mailType = mailType,
        onClickBack = {
            navController.popBackStack()
        },
        onClickDeleteMail = {
            viewModel.onAction(MailDetailAction.OnClickDeleteMail)
        },
        onClickReplyMail = {
            viewModel.onAction(MailDetailAction.OnClickReplyMail(mail))
        },
        onDismissDialog = {
            viewModel.onAction(MailDetailAction.OnDismissDialog)
        },
        onSubmitDeleteMail = {
            navController.previousBackStackEntry?.savedStateHandle?.set(
                HomeRoutes.HOME_ARG,
                DataMessageCondition(
                    dataCondition = DataCondition.Success,
                    message = "Mail successfully deleted",
                ),
            )

            viewModel.onAction(
                MailDetailAction.OnSubmitDeleteMail(
                    mail = mail,
                    isInboxMail = mailType == DrawerItemType.INBOX,
                )
            )
        },
    )
}

@Composable
private fun MailDetailScreen(
    viewState: MailDetailViewState,
    mail: Mail,
    mailType: DrawerItemType,
    onClickBack: () -> Unit,
    onClickDeleteMail: () -> Unit,
    onClickReplyMail: () -> Unit,
    onDismissDialog: () -> Unit,
    onSubmitDeleteMail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    LaunchedEffect(key1 = sheetState.currentValue) {
        if (sheetState.currentValue == ModalBottomSheetValue.Hidden  && !viewState.loading) {
            onDismissDialog()
        }
    }

    LaunchedEffect(key1 = viewState.dialogData) {
        if (viewState.dialogData != null) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = GmaylTheme.color.mist10,
        sheetContent = {
            when {
                viewState.dialogData != null -> BaseDialog(
                    data = viewState.dialogData,
                    isLoading = viewState.loading,
                    onClickNegative = onDismissDialog,
                    onClickPositive = onSubmitDeleteMail,
                )
                else -> Box(modifier = Modifier.size(1.dp))
            }
        },
    ) {
        Scaffold(
            topBar = {
                GmaylAppBar(
                    modifier = Modifier.background(color = GmaylTheme.color.mist10),
                    navigationIcon = R.drawable.ic_arrow_left,
                    onClickNavigationIcon = onClickBack,
                    title = null,
                    actions = {
                        IconButton(onClick = onClickDeleteMail) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = "icon delete",
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->
            MailDetailScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = GmaylTheme.color.mist10)
                    .padding(innerPadding),
                mail = mail,
                mailType = mailType,
                onClickReplyMail = onClickReplyMail,
            )
        }
    }
}

@Composable
private fun MailDetailScreenContent(
    mail: Mail,
    mailType: DrawerItemType,
    onClickReplyMail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = mail.subject,
            style = GmaylTheme.typography.titleMediumBold,
            color = GmaylTheme.color.mist100,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .background(color = GmaylTheme.color.autumn30, shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 2.dp),
            text = mailType.title,
            style = GmaylTheme.typography.contentSmallRegular,
            color = GmaylTheme.color.mist70,
        )
        Spacer(modifier = Modifier.height(16.dp))
        SenderItem(
            mail = mail,
            onClickReply = onClickReplyMail,
        )
    }
}

@Preview
@Composable
private fun PreviewMailDetailScreen() {
    MailDetailScreen(
        viewState = MailDetailViewState(),
        mail = SentMail(
            sender = User(name = "Bintang F.", email = "bintang@gmail.com"),
            receiver = User(email = "you@gmail.com"),
            subject = "[Tugas 1] Ini contoh subject aja [Tugas 1] Ini contoh subject aja",
            body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent interdum nibh at porttitor pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Maecenas mattis vitae nisl at convallis. Quisque rhoncus, felis id bibendum commodo, risus nulla faucibus magna, id eleifend eros ante non sapien. Quisque.",
            sentTime = "2023-03-05T13:15:30+07:00",
        ),
        mailType = DrawerItemType.INBOX,
        onClickBack = {},
        onClickDeleteMail = {},
        onClickReplyMail = {},
        onDismissDialog = {},
        onSubmitDeleteMail = {},
    )
}
