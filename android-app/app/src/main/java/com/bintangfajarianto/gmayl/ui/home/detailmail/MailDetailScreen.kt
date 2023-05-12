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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.core.navigation.HomeRoutes
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.general.AlertType
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
import com.bintangfajarianto.gmayl.ui.home.dialog.InputSymmetricKeyDialog
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylKeypair
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarHost
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarVisuals
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextSelection
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

    // Key
    var shouldDecrypt by remember {
        mutableStateOf(false)
    }
    var symmetricKey by remember {
        mutableStateOf(TextFieldValue())
    }

    MailDetailScreen(
        modifier = modifier,
        viewState = viewState,
        mail = mail,
        mailType = mailType,
        symmetricKey = symmetricKey,
        shouldDecrypt = shouldDecrypt,
        onChangeShouldDecrypt = { shouldDecrypt = it },
        onClickBack = { navController.popBackStack() },
        onClickDeleteMail = { viewModel.onAction(MailDetailAction.OnClickDeleteMail) },
        onClickDecryptMail = { viewModel.onAction(MailDetailAction.OnClickDecryptMail) },
        onClickReplyMail = { viewModel.onAction(MailDetailAction.OnClickReplyMail(mail)) },
        onDismissDialog = { viewModel.onAction(MailDetailAction.OnDismissDialog) },
        onDismissSnackBar = { viewModel.onAction(MailDetailAction.OnDismissSnackBar) },
        onInputSymmetricKey = { viewModel.onAction(MailDetailAction.OnInputSymmetricKey(it)) },
        OnSubmitDecryptMail = {
            symmetricKey = it
            viewModel.onAction(MailDetailAction.OnSubmitDecryptMail(mail.body, symmetricKey.text))
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
    symmetricKey: TextFieldValue,
    shouldDecrypt: Boolean,
    onChangeShouldDecrypt: (Boolean) -> Unit,
    onClickBack: () -> Unit,
    onClickDeleteMail: () -> Unit,
    onClickDecryptMail: () -> Unit,
    onClickReplyMail: () -> Unit,
    onDismissDialog: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onInputSymmetricKey: (String) -> Unit,
    OnSubmitDecryptMail: (TextFieldValue) -> Unit,
    onSubmitDeleteMail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = viewState.dataMsgCondition) {
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

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    LaunchedEffect(key1 = sheetState.currentValue) {
        if (sheetState.currentValue == ModalBottomSheetValue.Hidden  && !viewState.loading) {
            onDismissDialog()
        }
    }

    LaunchedEffect(key1 = viewState.dialogData, key2 = viewState.showDecryptionDialog) {
        if (viewState.dialogData != null || viewState.showDecryptionDialog) {
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
                viewState.showDecryptionDialog -> InputSymmetricKeyDialog(
                    key = symmetricKey,
                    title = stringResource(id = R.string.send_mail_symmetric_key_title),
                    hint = stringResource(id = R.string.send_mail_symmetric_key_hint),
                    errorMessage = viewState.errorMessageSymmetricKey,
                    enabled = viewState.validSymmetricKey,
                    loading = viewState.loading,
                    onChangeKey = onInputSymmetricKey,
                    onClickSave = OnSubmitDecryptMail,
                )
                else -> Box(modifier = Modifier.size(1.dp))
            }
        },
    ) {
        Scaffold(
            snackbarHost = { GmaylSnackBarHost(hostState = snackBarHostState) },
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
                viewState = viewState,
                mail = mail,
                mailType = mailType,
                symmetricKey = symmetricKey,
                shouldDecrypt = shouldDecrypt,
                onChangeShouldDecrypt = onChangeShouldDecrypt,
                onClickDecryptMail = onClickDecryptMail,
                onClickReplyMail = onClickReplyMail,
            )
        }
    }
}

@Composable
private fun MailDetailScreenContent(
    viewState: MailDetailViewState,
    mail: Mail,
    mailType: DrawerItemType,
    symmetricKey: TextFieldValue,
    shouldDecrypt: Boolean,
    onChangeShouldDecrypt: (Boolean) -> Unit,
    onClickDecryptMail: () -> Unit,
    onClickReplyMail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(state = rememberScrollState())) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = mail.subject,
            style = GmaylTheme.typography.titleMediumBold,
            color = GmaylTheme.color.mist100,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                modifier = Modifier
                    .background(color = GmaylTheme.color.autumn30, shape = CircleShape)
                    .padding(horizontal = 12.dp, vertical = 2.dp),
                text = mailType.title,
                style = GmaylTheme.typography.contentSmallRegular,
                color = GmaylTheme.color.mist70,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SenderItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            mail = mail,
            onClickReply = onClickReplyMail,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = mail.body,
            style = GmaylTheme.typography.contentSmallRegular,
            color = GmaylTheme.color.mist100,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = GmaylTheme.color.mist30)
        if (mail.encrypted) {
            Spacer(modifier = Modifier.height(4.dp))
            GmaylKeypair(
                keyContent = {
                    GmaylTextSelection(
                        title = stringResource(id = R.string.mail_detail_decrypt_message),
                        subtitle = symmetricKey.text,
                        prefixSubtitle = stringResource(id = R.string.send_mail_symmetric_key_title),
                        enabled = shouldDecrypt && !viewState.loading,
                        onClick = onClickDecryptMail,
                    )
                },
                pairContent = {
                    Switch(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        checked = shouldDecrypt,
                        enabled = !viewState.loading,
                        onCheckedChange = onChangeShouldDecrypt,
                        thumbContent = {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_lock),
                                contentDescription = "icon lock",
                            )
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = GmaylTheme.color.primary50,
                            checkedTrackColor = GmaylTheme.color.primary30,
                            checkedBorderColor = GmaylTheme.color.primary50,
                            checkedIconColor = GmaylTheme.color.primary10,
                            uncheckedThumbColor = GmaylTheme.color.primary50,
                            uncheckedTrackColor = GmaylTheme.color.primary10,
                            uncheckedBorderColor = GmaylTheme.color.primary50,
                            uncheckedIconColor = GmaylTheme.color.primary10,
                        ),
                    )
                },
            )
        }
        if (!viewState.decryptedMessage.isNullOrEmpty() && shouldDecrypt) {
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = GmaylTheme.color.mist30)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.mail_detail_decrypt_message_title),
                style = GmaylTheme.typography.titleSmallSemiBold,
                color = GmaylTheme.color.mist100,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = viewState.decryptedMessage,
                style = GmaylTheme.typography.contentSmallRegular,
                color = GmaylTheme.color.mist100,
            )
        }
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
        symmetricKey = TextFieldValue(),
        shouldDecrypt = false,
        onChangeShouldDecrypt = {},
        onClickBack = {},
        onClickDeleteMail = {},
        onClickDecryptMail = {},
        onClickReplyMail = {},
        onDismissDialog = {},
        onDismissSnackBar = {},
        onInputSymmetricKey = {},
        OnSubmitDecryptMail = {},
        onSubmitDeleteMail = {})
}
