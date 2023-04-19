package com.bintangfajarianto.gmayl.ui.send

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.auth.UserItem
import com.bintangfajarianto.gmayl.data.auth.UserItemResponse
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylKeypair
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrefixTextInput
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextInput

@Composable
fun SendMessageRoute() {

}

@Composable
private fun SendMessageScreen(
    userItem: UserItem,
    sendTo: TextFieldValue,
    subject: TextFieldValue,
    message: TextFieldValue,
    loading: Boolean,
    onClickBack: () -> Unit,
    updateSentTo: (TextFieldValue) -> Unit,
    updateSubject: (TextFieldValue) -> Unit,
    updateMessage: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    var encrypt by remember {
        mutableStateOf(false)
    }

    var digitalSign by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    val scrollState = rememberScrollState()

    val scrolling by remember {
        derivedStateOf {
            scrollState.value > 0
        }
    }

    val appBarColor by animateColorAsState(
        when {
            scrolling -> GmaylTheme.color.primary30
            else -> GmaylTheme.color.mist10
        }
    )

    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetContent = {

        },
    ) {
        Scaffold(
            topBar = {
                 GmaylAppBar(
                     modifier = Modifier.background(color = appBarColor),
                     navigationIcon = R.drawable.ic_arrow_left,
                     onClickNavigationIcon = onClickBack,
                     title = stringResource(id = R.string.send_message_title),
                 )
            },
        ) { innerPadding ->
            SendMessageScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                userItem = userItem,
                sendTo = sendTo,
                subject = subject,
                message = message,
                scrollState = scrollState,
                encrypt = encrypt,
                digitalSign = digitalSign,
                loading = loading,
                updateEncrypt = { encrypt = it },
                updateDigitalSign = { digitalSign = it },
                updateSentTo = updateSentTo,
                updateSubject = updateSubject,
                updateMessage = updateMessage,
            )
        }
    }
}

@Composable
private fun SendMessageScreenContent(
    userItem: UserItem,
    sendTo: TextFieldValue,
    subject: TextFieldValue,
    message: TextFieldValue,
    scrollState: ScrollState,
    encrypt: Boolean,
    digitalSign: Boolean,
    loading: Boolean,
    updateEncrypt: (Boolean) -> Unit,
    updateDigitalSign: (Boolean) -> Unit,
    updateSentTo: (TextFieldValue) -> Unit,
    updateSubject: (TextFieldValue) -> Unit,
    updateMessage: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .background(color = GmaylTheme.color.mist10),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        GmaylPrefixTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = TextFieldValue(userItem.email),
            enabled = false,
            prefix = stringResource(id = R.string.send_message_from),
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylPrefixTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = sendTo,
            prefix = stringResource(id = R.string.send_message_to),
            onValueChange = updateSentTo,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = subject,
            title = stringResource(id = R.string.send_message_subject),
            hint = stringResource(id = R.string.send_message_subject_hint),
            onValueChange = updateSubject,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylTextInput(
            modifier = Modifier.padding(horizontal = 16.dp),
            value = message,
            title = stringResource(id = R.string.send_message_message),
            hint = stringResource(id = R.string.send_message_message_hint),
            singleLine = false,
            onValueChange = updateMessage,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(label = stringResource(id = R.string.send_message_encrypt_message)) {
            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = encrypt,
                onCheckedChange = updateEncrypt,
                thumbContent = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "icon lock",
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = GmaylTheme.color.primary50,
                    checkedTrackColor = GmaylTheme.color.primary10,
                    checkedBorderColor = GmaylTheme.color.primary50,
                    checkedIconColor = GmaylTheme.color.primary10,
                    uncheckedThumbColor = GmaylTheme.color.primary50,
                    uncheckedTrackColor = GmaylTheme.color.primary10,
                    uncheckedBorderColor = GmaylTheme.color.primary50,
                    uncheckedIconColor = GmaylTheme.color.primary10,
                ),
            )
        }
        Divider(color = GmaylTheme.color.mist30)
        GmaylKeypair(label = stringResource(id = R.string.send_message_digital_sign)) {
            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = digitalSign,
                onCheckedChange = updateDigitalSign,
                thumbContent = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_sign),
                        contentDescription = "icon sign",
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = GmaylTheme.color.primary50,
                    checkedTrackColor = GmaylTheme.color.primary10,
                    checkedBorderColor = GmaylTheme.color.primary50,
                    checkedIconColor = GmaylTheme.color.primary10,
                    uncheckedThumbColor = GmaylTheme.color.primary50,
                    uncheckedTrackColor = GmaylTheme.color.primary10,
                    uncheckedBorderColor = GmaylTheme.color.primary50,
                    uncheckedIconColor = GmaylTheme.color.primary10,
                ),
            )
        }
        Divider(color = GmaylTheme.color.mist30)
        Spacer(modifier = Modifier.weight(1F))
        GmaylPrimaryButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            label = stringResource(id = R.string.send_message_send),
            loading = loading,
            enabled = true,
            onClick = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun PreviewSendMessageScreen() {
    SendMessageScreen(
        userItem = UserItemResponse("", "13519138@std.stei.itb.ac.id", ""),
        sendTo = TextFieldValue(),
        subject = TextFieldValue(),
        message = TextFieldValue(),
        updateSentTo = {},
        updateSubject = {},
        updateMessage = {},
        loading = false,
        onClickBack = {},
    )
}
