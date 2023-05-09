package com.bintangfajarianto.gmayl.ui.auth

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.feature.vm.auth.LoginAction
import com.bintangfajarianto.gmayl.feature.vm.auth.LoginViewModel
import com.bintangfajarianto.gmayl.feature.vm.auth.LoginViewState
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.base.BaseDialog
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylTextInput
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberViewModel

@Composable
fun LoginRoute(modifier: Modifier = Modifier) {
    val viewModel: LoginViewModel by rememberViewModel()
    val viewState by viewModel.stateFlow.collectAsState()

    var email by remember {
        mutableStateOf(TextFieldValue())
    }
    var password by remember {
        mutableStateOf(TextFieldValue())
    }
    var visiblePassword by remember {
        mutableStateOf(false)
    }

    LoginScreen(
        modifier = modifier,
        viewState = viewState,
        email = email,
        password = password,
        visiblePassword = visiblePassword,
        onChangeEmail = {
            email = it
            viewModel.onAction(LoginAction.OnInputEmail(email.text))
        },
        onChangePassword = {
            password = it
            viewModel.onAction(LoginAction.OnInputPassword(password.text))
        },
        onChangeVisiblePassword = {
            visiblePassword = it
        },
        onDismissDialog = {
            viewModel.onAction(LoginAction.OnDismissDialog)
        },
        onClickLogin = {
            viewModel.onAction(LoginAction.OnClickLogin(email.text, password.text))
        },
    )
}

@Composable
private fun LoginScreen(
    viewState: LoginViewState,
    email: TextFieldValue,
    password: TextFieldValue,
    visiblePassword: Boolean,
    onChangeEmail: (TextFieldValue) -> Unit,
    onChangePassword: (TextFieldValue) -> Unit,
    onChangeVisiblePassword: (Boolean) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    LaunchedEffect(key1 = sheetState.currentValue) {
        if (sheetState.currentValue == ModalBottomSheetValue.Hidden) {
            onDismissDialog()
        }
    }

    LaunchedEffect(key1 = viewState.dialogData) {
        if (viewState.dialogData != null) {
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
                viewState.dialogData != null -> BaseDialog(
                    data = viewState.dialogData,
                    onClickPositive = {
                        coroutineScope.launch { sheetState.hide() }
                    },
                )
                else -> Box(modifier = Modifier.size(1.dp))
            }
        }
    ) {
        Scaffold {
            LoginScreenContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                viewState = viewState,
                email = email,
                password = password,
                visiblePassword = visiblePassword,
                onChangeEmail = onChangeEmail,
                onChangePassword = onChangePassword,
                onChangeVisiblePassword = onChangeVisiblePassword,
                onClickLogin = onClickLogin,
            )
        }
    }
}

@Composable
private fun LoginScreenContent(
    viewState: LoginViewState,
    email: TextFieldValue,
    password: TextFieldValue,
    visiblePassword: Boolean,
    onChangeEmail: (TextFieldValue) -> Unit,
    onChangePassword: (TextFieldValue) -> Unit,
    onChangeVisiblePassword: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .background(color = GmaylTheme.color.mist10),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_gmail),
                contentDescription = "gmail logo",
            )
            Spacer(modifier = Modifier.height(24.dp))
            GmaylTextInput(
                value = email,
                title = stringResource(id = R.string.auth_email),
                hint = stringResource(id = R.string.auth_email_hint),
                errorMessage = viewState.errorMessageEmail,
                enabled = !viewState.loading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = onChangeEmail,
            )
            Spacer(modifier = Modifier.height(16.dp))
            GmaylTextInput(
                value = password,
                title = stringResource(id = R.string.auth_password),
                hint = stringResource(id = R.string.auth_password_hint),
                errorMessage = viewState.errorMessagePassword,
                enabled = !viewState.loading,
                visualTransformation = if (visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    Crossfade(
                        modifier = Modifier.clickable {
                            onChangeVisiblePassword(!visiblePassword)
                        },
                        targetState = visiblePassword) { visiblePassword ->
                        if (visiblePassword) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = stringResource(id = R.string.auth_hide_password),
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(id = R.string.auth_show_password),
                            )
                        }
                    }
                },
                onValueChange = onChangePassword,
            )
            Spacer(modifier = Modifier.height(24.dp))
            GmaylPrimaryButton(
                label = stringResource(id = R.string.auth_login),
                loading = viewState.loading,
                enabled = viewState.validEmail && viewState.validPassword && !viewState.loading,
                onClick = onClickLogin,
            )
        }
    }
}
