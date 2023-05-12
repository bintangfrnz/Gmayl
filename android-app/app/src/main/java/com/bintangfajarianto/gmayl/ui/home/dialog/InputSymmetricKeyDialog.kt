package com.bintangfajarianto.gmayl.ui.home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.base.HandlerBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextInput

@Composable
fun InputSymmetricKeyDialog(
    key: TextFieldValue,
    onChangeKey: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    hint: String = "",
    errorMessage: String? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClickSave: (TextFieldValue) -> Unit,
) {
    var newKey by remember {
        mutableStateOf(value = key)
    }

    Column(
        modifier = modifier
            .background(color = GmaylTheme.color.mist10)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        HandlerBar()
        Spacer(modifier = Modifier.height(24.dp))
        GmaylTextInput(
            value = newKey,
            onValueChange = {
                newKey = it
                onChangeKey(newKey.text)
            },
            title = title,
            hint = hint,
            enabled = !loading,
            errorMessage = errorMessage,
        )
        Spacer(modifier = Modifier.height(16.dp))
        GmaylPrimaryButton(
            label = stringResource(id = R.string.send_mail_submit_key),
            loading = loading,
            enabled = newKey != key && enabled,
            onClick = { onClickSave(newKey) },
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun PreviewInputSymmetricKeyDialog() {
    InputSymmetricKeyDialog(
        key = TextFieldValue("bintangfrnz_13519138"),
        title = stringResource(id = R.string.send_mail_symmetric_key_title),
        hint = stringResource(id = R.string.send_mail_symmetric_key_hint),
        onChangeKey = {},
        onClickSave = {},
    )
}
