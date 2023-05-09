package com.bintangfajarianto.gmayl.ui.home.sendmail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.feature.vm.home.sendmail.SendMailAction
import com.bintangfajarianto.gmayl.feature.vm.home.sendmail.SendMailViewModel
import com.bintangfajarianto.gmayl.feature.vm.home.sendmail.SendMailViewState
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.home.dialog.InputKeyDialog
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylKeypair
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrefixTextInput
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarHost
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarVisuals
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextInput
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextSelection
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.kodein.di.compose.rememberViewModel

@Composable
fun SendMailRoute(
    sender: User,
    receiver: User,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: SendMailViewModel by rememberViewModel()
    val viewState by viewModel.stateFlow.collectAsState()

    val keyboard = LocalSoftwareKeyboardController.current

    // Text Input
    var sendTo by remember {
        mutableStateOf(TextFieldValue(receiver.email))
    }
    var subject by remember {
        mutableStateOf(TextFieldValue())
    }
    var message by remember {
        mutableStateOf(TextFieldValue())
    }

    // Key
    var shouldEncrypt by remember {
        mutableStateOf(false)
    }
    var shouldAddDigitalSign by remember {
        mutableStateOf(false)
    }
    var publicKey by remember {
        mutableStateOf(TextFieldValue())
    }
    var symmetricKey by remember {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(Unit) {
        if (!viewState.isInitSendToEmail) {
            viewModel.onAction(SendMailAction.InitSendToEmail(sendTo.text))
        }
    }

    SendMessageScreen(
        modifier = modifier,
        viewState = viewState,
        sender = sender,
        sendTo = sendTo,
        subject = subject,
        message = message,
        symmetricKey = symmetricKey,
        publicKey = publicKey,
        shouldEncrypt = shouldEncrypt,
        shouldAddDigitalSign = shouldAddDigitalSign,
        onChangeShouldEncrypt = { shouldEncrypt = it },
        onChangeShouldAddDigitalSign = { shouldAddDigitalSign = it },
        onClickBack = { navController.popBackStack() },
        onClickEncryptMail = { viewModel.onAction(SendMailAction.OnClickEncryptMail) },
        onClickSignMail = { viewModel.onAction(SendMailAction.OnClickSignMail) },
        onDismissDialog = { viewModel.onAction(SendMailAction.OnDismissDialog) },
        onDismissSnackBar = { viewModel.onAction(SendMailAction.OnDismissSnackBar) },
        onInputSymmetricKey = { viewModel.onAction(SendMailAction.OnInputSymmetricKey(it)) },
        onSavePublicKey = {
            publicKey = it
            when {
                publicKey.text.isNotEmpty() -> viewModel.onAction(
                    SendMailAction.OnReceiveDataCondition(
                        dataMsgCondition = DataMessageCondition(
                            dataCondition = DataCondition.Success,
                            message = "Public Key successfully changed",
                        ),
                    ),
                )
                else -> {
                    shouldAddDigitalSign = false
                    viewModel.onAction(
                        SendMailAction.OnReceiveDataCondition(
                            dataMsgCondition = DataMessageCondition(
                                dataCondition = DataCondition.Failure,
                                message = "Public Key successfully deleted",
                            ),
                        ),
                    )
                }
            }
        },
        onSaveSymmetricKey = {
            symmetricKey = it
            when {
                symmetricKey.text.isNotEmpty() -> viewModel.onAction(
                    SendMailAction.OnReceiveDataCondition(
                        dataMsgCondition = DataMessageCondition(
                            dataCondition = DataCondition.Success,
                            message = "Symmetric Key successfully changed",
                        ),
                    ),
                )
                else -> {
                    shouldEncrypt = false
                    viewModel.onAction(
                        SendMailAction.OnReceiveDataCondition(
                            dataMsgCondition = DataMessageCondition(
                                dataCondition = DataCondition.Failure,
                                message = "Symmetric Key successfully deleted",
                            ),
                        ),
                    )
                }
            }
        },
        updateSentTo = {
            sendTo = it
            viewModel.onAction(SendMailAction.OnInputSendToEmail(sendTo.text))
        },
        updateSubject = { subject = it },
        updateMessage = { message = it },
        onClickSendMail = {
            keyboard?.hide()
            navController.getBackStackEntry(
                HomeRoutes.HOME_ROUTE,
            ).savedStateHandle[HomeRoutes.HOME_ARG] = DataMessageCondition(
                dataCondition = DataCondition.Success,
                message = "Mail successfully sent",
            )

            viewModel.onAction(
                SendMailAction.OnClickSendMail(
                    mail = InboxMail(
                        sender = sender,
                        receiver = User(email = sendTo.text),
                        subject = subject.text,
                        body = message.text,
                        sentTime = Clock.System.now().toString(),
                        encrypted = shouldEncrypt,
                    ),
                    publicKey = publicKey.text,
                    symmetricKey = symmetricKey.text,
                )
            )
        },
    )
}

@Composable
private fun SendMessageScreen(
    viewState: SendMailViewState,
    sender: User,
    sendTo: TextFieldValue,
    subject: TextFieldValue,
    message: TextFieldValue,
    publicKey: TextFieldValue,
    symmetricKey: TextFieldValue,
    shouldEncrypt: Boolean,
    shouldAddDigitalSign: Boolean,
    onChangeShouldEncrypt: (Boolean) -> Unit,
    onChangeShouldAddDigitalSign: (Boolean) -> Unit,
    onClickBack: () -> Unit,
    onClickEncryptMail: () -> Unit,
    onClickSignMail: () -> Unit,
    onDismissDialog: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onInputSymmetricKey: (String) -> Unit,
    onSavePublicKey: (TextFieldValue) -> Unit,
    onSaveSymmetricKey: (TextFieldValue) -> Unit,
    updateSentTo: (TextFieldValue) -> Unit,
    updateSubject: (TextFieldValue) -> Unit,
    updateMessage: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClickSendMail: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

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

    LaunchedEffect(key1 = viewState.showEncryptionDialog, key2 = viewState.showDigitalSignDialog) {
        if (viewState.showEncryptionDialog || viewState.showDigitalSignDialog) {
            sheetState.show()
        }
    }

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = GmaylTheme.color.mist10,
        sheetContent = {
            when {
                viewState.showEncryptionDialog -> InputKeyDialog(
                    key = symmetricKey,
                    title = stringResource(id = R.string.send_mail_symmetric_key_title),
                    hint = stringResource(id = R.string.send_mail_symmetric_key_hint),
                    errorMessage = viewState.errorMessageSymmetricKey,
                    enabled = viewState.validSymmetricKey,
                    onChangeKey = onInputSymmetricKey,
                    onClickSave = {
                        coroutineScope.launch { sheetState.hide() }
                        onSaveSymmetricKey(it)
                    },
                )
                viewState.showDigitalSignDialog -> InputKeyDialog(
                    key = publicKey,
                    title = stringResource(id = R.string.send_mail_public_key_title),
                    hint = stringResource(id = R.string.send_mail_public_key_hint),
                    onChangeKey = {},
                    onClickSave = {
                        coroutineScope.launch { sheetState.hide() }
                        onSavePublicKey(it)
                    },
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
                     title = stringResource(id = R.string.send_mail_title),
                 )
            },
        ) { innerPadding ->
            SendMessageScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = GmaylTheme.color.mist10)
                    .padding(innerPadding),
                viewState = viewState,
                sender = sender,
                sendTo = sendTo,
                subject = subject,
                message = message,
                publicKey = publicKey,
                symmetricKey = symmetricKey,
                shouldEncrypt = shouldEncrypt,
                shouldAddDigitalSign = shouldAddDigitalSign,
                onChangeShouldEncrypt = onChangeShouldEncrypt,
                onChangeShouldAddDigitalSign = onChangeShouldAddDigitalSign,
                onClickEncryptMail = onClickEncryptMail,
                onClickSignMail = onClickSignMail,
                updateSentTo = updateSentTo,
                updateSubject = updateSubject,
                updateMessage = updateMessage,
                onClickSendMail = onClickSendMail,
            )
        }
    }
}

@Composable
private fun SendMessageScreenContent(
    viewState: SendMailViewState,
    sender: User,
    sendTo: TextFieldValue,
    subject: TextFieldValue,
    message: TextFieldValue,
    publicKey: TextFieldValue,
    symmetricKey: TextFieldValue,
    shouldEncrypt: Boolean,
    shouldAddDigitalSign: Boolean,
    onChangeShouldEncrypt: (Boolean) -> Unit,
    onChangeShouldAddDigitalSign: (Boolean) -> Unit,
    onClickEncryptMail: () -> Unit,
    onClickSignMail: () -> Unit,
    updateSentTo: (TextFieldValue) -> Unit,
    updateSubject: (TextFieldValue) -> Unit,
    updateMessage: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClickSendMail: () -> Unit,
) {
    Column(modifier = modifier.verticalScroll(state = rememberScrollState())) {
        Spacer(modifier = Modifier.height(8.dp))
        GmaylPrefixTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = TextFieldValue(sender.email),
            enabled = false,
            prefix = stringResource(id = R.string.send_mail_from),
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        GmaylPrefixTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sendTo,
            prefix = stringResource(id = R.string.send_mail_to),
            errorMessage = viewState.errorMessageSendToEmail,
            enabled = !viewState.loading,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = updateSentTo,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = subject,
            title = stringResource(id = R.string.send_mail_subject),
            hint = stringResource(id = R.string.send_mail_subject_hint),
            enabled = !viewState.loading,
            onValueChange = updateSubject,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = message,
            title = stringResource(id = R.string.send_mail_message),
            hint = stringResource(id = R.string.send_mail_message_hint),
            enabled = !viewState.loading,
            singleLine = false,
            onValueChange = updateMessage,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(
            keyContent = {
                 GmaylTextSelection(
                     title = stringResource(id = R.string.send_mail_encrypt_message),
                     subtitle = symmetricKey.text,
                     prefixSubtitle = stringResource(id = R.string.send_mail_symmetric_key_title),
                     enabled = shouldEncrypt && !viewState.loading,
                     onClick = onClickEncryptMail,
                 )
            },
            pairContent = {
                Switch(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    checked = shouldEncrypt,
                    enabled = !viewState.loading,
                    onCheckedChange = onChangeShouldEncrypt,
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
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(
            keyContent = {
                GmaylTextSelection(
                    title = stringResource(id = R.string.send_mail_digital_sign),
                    subtitle = publicKey.text,
                    prefixSubtitle = stringResource(id = R.string.send_mail_public_key_title),
                    enabled = shouldAddDigitalSign && !viewState.loading,
                    onClick = onClickSignMail,
                )
            },
            pairContent = {
                Switch(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    checked = shouldAddDigitalSign,
                    enabled = !viewState.loading,
                    onCheckedChange = onChangeShouldAddDigitalSign,
                    thumbContent = {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.ic_sign),
                            contentDescription = "icon sign",
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
            }
        )
        Divider(color = GmaylTheme.color.mist30)
        Spacer(modifier = Modifier.weight(1F))
        Spacer(modifier = Modifier.height(16.dp))
        GmaylPrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            label = stringResource(id = R.string.send_mail_send),
            loading = viewState.loading,
            enabled = viewState.validSendToEmail && subject.text.isNotBlank() && message.text.isNotBlank() && !viewState.loading,
            onClick = onClickSendMail,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun PreviewSendMessageScreen() {
    SendMessageScreen(
        viewState = SendMailViewState(),
        sender = User(email = "admin@gmail.com"),
        sendTo = TextFieldValue("someone@gmail.com"),
        subject = TextFieldValue(),
        message = TextFieldValue(),
        publicKey = TextFieldValue(),
        symmetricKey = TextFieldValue("This is symmetric key"),
        shouldEncrypt = true,
        shouldAddDigitalSign = false,
        onClickBack = {},
        onDismissDialog = {},
        onDismissSnackBar = {},
        onClickEncryptMail = {},
        onClickSignMail = {},
        onInputSymmetricKey = {},
        onChangeShouldEncrypt = {},
        onChangeShouldAddDigitalSign = {},
        onSavePublicKey = {},
        onSaveSymmetricKey = {},
        updateSentTo = {},
        updateSubject = {},
        updateMessage = {},
        onClickSendMail = {},
    )
}
