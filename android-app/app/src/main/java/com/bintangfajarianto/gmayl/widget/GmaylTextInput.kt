package com.bintangfajarianto.gmayl.widget

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.theme.GmaylTheme

@Composable
fun GmaylTextInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    title: String? = null,
    hint: String? = null,
    helper: String? = null,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    inputLimit: Int = Int.MAX_VALUE,
    showInputLimit: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onFocusChange: ((Boolean) -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val showSupportingText = showInputLimit || !helper.isNullOrEmpty() || !errorMessage.isNullOrEmpty()

    val backgroundColor by animateColorAsState(
        targetValue = if (enabled) GmaylTheme.color.mist10 else GmaylTheme.color.mist20,
        label = "background color",
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            !errorMessage.isNullOrEmpty() -> GmaylTheme.color.rose50
            isFocused -> GmaylTheme.color.primary50
            else -> GmaylTheme.color.mist30
        },
        label = "border color",
    )
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = GmaylTheme.typography.contentSmallRegular,
                color = if (enabled) GmaylTheme.color.mist100 else GmaylTheme.color.mist50,
            )
        }
        GmaylTextFieldContent(
            value = value,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            enabled = enabled,
            singleLine = singleLine,
            readOnly = readOnly,
            inputLimit = inputLimit,
            onFocusChange = {
                if (onFocusChange != null) {
                    onFocusChange(it)
                } else {
                    isFocused = it
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (trailingIcon == null) {
                    AnimatedContent(targetState = isFocused, modifier = Modifier.size(24.dp)) {
                        if (it) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { onValueChange(TextFieldValue()) },
                                painter = painterResource(id = R.drawable.ic_circle_close),
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    trailingIcon.invoke()
                }
            },
            hint = hint,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onValueChange = onValueChange,
        )
        AnimatedVisibility(visible = showSupportingText) {
            GmaylTextInputSupportingText(
                modifier = Modifier.fillMaxWidth(),
                errorMessage = errorMessage,
                helper = helper,
                limitCounterLabel = if (showInputLimit) {
                    "${value.text.length}/$inputLimit"
                } else {
                    ""
                },
            )
        }
    }
}

@Composable
private fun GmaylTextFieldContent(
    value: TextFieldValue,
    backgroundColor: Color,
    borderColor: Color,
    enabled: Boolean,
    singleLine: Boolean,
    readOnly: Boolean,
    inputLimit: Int,
    onFocusChange: (Boolean) -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit),
    hint: String?,
    visualTransformation: VisualTransformation,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                brush = SolidColor(borderColor),
                shape = RoundedCornerShape(8.dp),
            )
            .onFocusChanged {
                onFocusChange(it.isFocused)
            },
        enabled = enabled,
        readOnly = readOnly,
        value = value,
        onValueChange = {
            if (it.text.length <= inputLimit) onValueChange(it)
        },
        singleLine = singleLine,
        textStyle = GmaylTheme.typography.contentMediumRegular,
        placeholder = {
            if (hint != null) {
                Text(
                    text = hint,
                    style = GmaylTheme.typography.contentMediumRegular,
                    color = GmaylTheme.color.mist50,
                )
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.textFieldColors(
            textColor = GmaylTheme.color.mist100,
            disabledTextColor = GmaylTheme.color.mist50,
            containerColor = backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = GmaylTheme.color.mist100,
        ),
    )
}

@Composable
private fun GmaylTextInputSupportingText(
    helper: String?,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    limitCounterLabel: String = "",
) {
    val displayedText = when {
        !errorMessage.isNullOrEmpty() -> errorMessage
        !helper.isNullOrEmpty() -> helper
        else -> ""
    }
    val color by animateColorAsState(
        targetValue = if (errorMessage.isNullOrEmpty()) {
            GmaylTheme.color.mist60
        } else {
            GmaylTheme.color.rose50
        },
        label = "text color",
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.weight(.8f),
            text = displayedText,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = GmaylTheme.typography.contentSmallRegular,
        )
        if (limitCounterLabel.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(.2f),
                text = limitCounterLabel,
                maxLines = 1,
                textAlign = TextAlign.End,
                color = GmaylTheme.color.mist60,
                style = GmaylTheme.typography.contentSmallRegular,
            )
        }
    }
}
