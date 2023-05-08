package com.bintangfajarianto.gmayl.ui.widget

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylPrefixTextInput(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    prefix: String = "",
    hint: String? = null,
    helper: String? = null,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (TextFieldValue) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = when {
            !errorMessage.isNullOrEmpty() -> GmaylTheme.color.rose50
            isFocused -> GmaylTheme.color.primary50
            else -> GmaylTheme.color.mist30
        },
    )
    val prefixTextColor by animateColorAsState(targetValue = if (enabled) GmaylTheme.color.mist100 else GmaylTheme.color.mist50)

    GmaylTextInput(
        modifier = modifier.onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        hint = hint,
        helper = helper,
        errorMessage = errorMessage,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .height(56.dp)
                    .widthIn(min = 46.dp),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = prefix,
                    color = prefixTextColor,
                    style = GmaylTheme.typography.contentSmallRegular,
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(color = borderColor),
                )
            }
        },
    )
}
