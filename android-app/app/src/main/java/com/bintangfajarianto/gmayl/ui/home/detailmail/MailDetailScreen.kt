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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.bintangfajarianto.gmayl.ui.home.dialog.InputKeyPairDialog
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

    val isSigned by rememberSaveable {
        mutableStateOf(
            mail.signature != ("" to "")
        )
    }

    // Key
    var shouldDecrypt by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldVerifyDigitalSign by rememberSaveable {
        mutableStateOf(false)
    }
    var publicKey by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var symmetricKey by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(key1 = viewState.verifiedMail) {
        when (viewState.verifiedMail) {
            true -> viewModel.onAction(
                MailDetailAction.OnReceiveDataCondition(
                    dataMsgCondition = DataMessageCondition(
                        dataCondition = DataCondition.Success,
                        message = "Email Verified",
                    ),
                )
            )
            false -> viewModel.onAction(
                MailDetailAction.OnReceiveDataCondition(
                    dataMsgCondition = DataMessageCondition(
                        dataCondition = DataCondition.Failure,
                        message = "Email Not Verified",
                    ),
                )
            )
            null -> return@LaunchedEffect
        }
    }

    MailDetailScreen(
        modifier = modifier,
        viewState = viewState,
        mail = mail,
        mailType = mailType,
        symmetricKey = symmetricKey,
        publicKey = publicKey,
        isSigned = isSigned,
        shouldDecrypt = shouldDecrypt,
        shouldVerifyDigitalSign = shouldVerifyDigitalSign,
        onChangeShouldDecrypt = { shouldDecrypt = it },
        onChangeShouldVerifyDigitalSign = { shouldVerifyDigitalSign = it },
        onClickBack = { navController.popBackStack() },
        onClickDeleteMail = { viewModel.onAction(MailDetailAction.OnClickDeleteMail) },
        onClickDecryptMail = { viewModel.onAction(MailDetailAction.OnClickDecryptMail) },
        onClickVerifyMail = { viewModel.onAction(MailDetailAction.OnClickVerifyMail) },
        onClickReplyMail = { viewModel.onAction(MailDetailAction.OnClickReplyMail(mail)) },
        onClickNavigateToKeyGenerator = { viewModel.onAction(MailDetailAction.OnClickNavigateToKeyGenerator) },
        onDismissDialog = { viewModel.onAction(MailDetailAction.OnDismissDialog) },
        onDismissSnackBar = { viewModel.onAction(MailDetailAction.OnDismissSnackBar) },
        onInputSymmetricKey = { viewModel.onAction(MailDetailAction.OnInputSymmetricKey(it)) },
        onSubmitDecryptMail = {
            symmetricKey = it

            val hexBody = when {
                isSigned -> mail.body.split("\n\n<ds>")[0]
                else -> mail.body
            }

            viewModel.onAction(MailDetailAction.OnSubmitDecryptMail(hexBody, symmetricKey.text))
        },
        onSubmitVerifyMail = {
            publicKey = it
            shouldVerifyDigitalSign = false

            val plainBody = when {
                mail.encrypted -> viewState.decryptedMessage.orEmpty()
                else -> mail.body.split("\n\n<ds>")[0]
            }

            viewModel.onAction(
                MailDetailAction.OnSubmitVerifyMail(
                    plainBody = plainBody,
                    publicKey = publicKey.text,
                    r = mail.signature.first,
                    s = mail.signature.second,
                )
            )
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
    publicKey: TextFieldValue,
    symmetricKey: TextFieldValue,
    isSigned: Boolean,
    shouldDecrypt: Boolean,
    shouldVerifyDigitalSign: Boolean,
    onChangeShouldDecrypt: (Boolean) -> Unit,
    onChangeShouldVerifyDigitalSign: (Boolean) -> Unit,
    onClickBack: () -> Unit,
    onClickDeleteMail: () -> Unit,
    onClickDecryptMail: () -> Unit,
    onClickVerifyMail: () -> Unit,
    onClickReplyMail: () -> Unit,
    onClickNavigateToKeyGenerator: () -> Unit,
    onDismissDialog: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onInputSymmetricKey: (String) -> Unit,
    onSubmitDecryptMail: (TextFieldValue) -> Unit,
    onSubmitVerifyMail: (TextFieldValue) -> Unit,
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

    LaunchedEffect(
        key1 = viewState.dialogData,
        key2 = viewState.showDecryptionDialog,
        key3 = viewState.showDigitalSignDialog,
    ) {
        if (viewState.dialogData != null || viewState.showDecryptionDialog || viewState.showDigitalSignDialog) {
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
                    onClickSave = onSubmitDecryptMail,
                )
                viewState.showDigitalSignDialog -> InputKeyPairDialog(
                    key = publicKey,
                    title = stringResource(id = R.string.mail_detail_public_key_title),
                    hint = stringResource(id = R.string.mail_detail_public_key_hint),
                    info = stringResource(id = R.string.mail_detail_check_public_key),
                    loading = viewState.loading,
                    onClickNavigateToKeyGenerator = onClickNavigateToKeyGenerator,
                    onClickSave = onSubmitVerifyMail,
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
                publicKey = publicKey,
                symmetricKey = symmetricKey,
                isSigned = isSigned,
                shouldDecrypt = shouldDecrypt,
                shouldVerifyDigitalSign = shouldVerifyDigitalSign,
                onChangeShouldDecrypt = onChangeShouldDecrypt,
                onChangeShouldVerifyDigitalSign = onChangeShouldVerifyDigitalSign,
                onClickDecryptMail = onClickDecryptMail,
                onClickVerifyMail = onClickVerifyMail,
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
    publicKey: TextFieldValue,
    symmetricKey: TextFieldValue,
    isSigned: Boolean,
    shouldDecrypt: Boolean,
    shouldVerifyDigitalSign: Boolean,
    onChangeShouldDecrypt: (Boolean) -> Unit,
    onChangeShouldVerifyDigitalSign: (Boolean) -> Unit,
    onClickDecryptMail: () -> Unit,
    onClickVerifyMail: () -> Unit,
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
            GmaylKeypair(
                modifier = Modifier.padding(vertical = 4.dp),
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
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = GmaylTheme.color.mist30)
        }

        if (isSigned && (!mail.encrypted || !viewState.decryptedMessage.isNullOrEmpty())) {
            GmaylKeypair(
                modifier = Modifier.padding(vertical = 4.dp),
                keyContent = {
                    GmaylTextSelection(
                        title = stringResource(id = R.string.mail_detail_digital_sign),
                        subtitle = publicKey.text,
                        prefixSubtitle = stringResource(id = R.string.mail_detail_public_key_title),
                        enabled = shouldVerifyDigitalSign && !viewState.loading,
                        onClick = onClickVerifyMail,
                    )
                },
                pairContent = {
                    Switch(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        checked = shouldVerifyDigitalSign,
                        enabled = !viewState.loading,
                        onCheckedChange = onChangeShouldVerifyDigitalSign,
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
        publicKey = TextFieldValue(),
        symmetricKey = TextFieldValue(),
        isSigned = false,
        shouldDecrypt = false,
        shouldVerifyDigitalSign = false,
        onChangeShouldDecrypt = {},
        onChangeShouldVerifyDigitalSign = {},
        onClickBack = {},
        onClickDeleteMail = {},
        onClickDecryptMail = {},
        onClickVerifyMail = {},
        onClickReplyMail = {},
        onClickNavigateToKeyGenerator = {},
        onDismissDialog = {},
        onDismissSnackBar = {},
        onInputSymmetricKey = {},
        onSubmitDecryptMail = {},
        onSubmitVerifyMail = {},
        onSubmitDeleteMail = {},
    )
}
