package com.bintangfajarianto.gmayl.ui.crypto

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bintangfajarianto.gmayl.R
import com.bintangfajarianto.gmayl.data.model.general.AlertType
import com.bintangfajarianto.gmayl.data.model.general.DataCondition
import com.bintangfajarianto.gmayl.feature.vm.crypto.KeyGeneratorAction
import com.bintangfajarianto.gmayl.feature.vm.crypto.KeyGeneratorViewModel
import com.bintangfajarianto.gmayl.feature.vm.crypto.KeyGeneratorViewState
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import com.bintangfajarianto.gmayl.ui.base.BaseDialog
import com.bintangfajarianto.gmayl.ui.widget.GmaylAppBar
import com.bintangfajarianto.gmayl.ui.widget.GmaylCopyableText
import com.bintangfajarianto.gmayl.ui.widget.GmaylPrimaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylSecondaryButton
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarHost
import com.bintangfajarianto.gmayl.ui.widget.GmaylSnackBarVisuals
import com.bintangfajarianto.gmayl.ui.widget.gmaylShimmerBrush
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberViewModel

@Composable
fun KeyGeneratorRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: KeyGeneratorViewModel by rememberViewModel()
    val viewState by viewModel.stateFlow.collectAsState()

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(Unit) {
        if (!viewState.isInitKeyPair) {
            viewModel.onAction(KeyGeneratorAction.InitKeyPair)
        }
    }

    KeyGeneratorScreen(
        modifier = modifier,
        viewState = viewState,
        onClickBack = {
            navController.popBackStack()
        },
        onClickCopyPrivateKey = {
            clipboardManager.setText(AnnotatedString(viewState.privateKey.orEmpty()))
            Toast.makeText(context, "Private key copied", Toast.LENGTH_SHORT).show()
        },
        onClickCopyPublicKey = {
            clipboardManager.setText(AnnotatedString(viewState.publicKey.orEmpty()))
            Toast.makeText(context, "Public key copied", Toast.LENGTH_SHORT).show()
        },
        onClickGenerateNewKey = {
            viewModel.onAction(KeyGeneratorAction.OnClickGenerateNewKey)
        },
        onClickResetKey = {
            viewModel.onAction(KeyGeneratorAction.OnClickResetKey)
        },
        onDismissDialog = {
            viewModel.onAction(KeyGeneratorAction.OnDismissDialog)
        },
        onDismissSnackBar = {
            viewModel.onAction(KeyGeneratorAction.OnDismissSnackBar)
        },
        onSubmitResetKey = {
            viewModel.onAction(KeyGeneratorAction.OnSubmitResetKey)
        },
    )
}

@Composable
private fun KeyGeneratorScreen(
    viewState: KeyGeneratorViewState,
    onClickBack: () -> Unit,
    onClickCopyPrivateKey: () -> Unit,
    onClickCopyPublicKey: () -> Unit,
    onClickGenerateNewKey: () -> Unit,
    onClickResetKey: () -> Unit,
    onDismissDialog: () -> Unit,
    onDismissSnackBar: () -> Unit,
    onSubmitResetKey: () -> Unit,
    modifier: Modifier = Modifier,
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
        if (sheetState.currentValue == ModalBottomSheetValue.Hidden && !viewState.loading && !viewState.fetching) {
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
                    onClickNegative = { coroutineScope.launch { sheetState.hide() } },
                    onClickPositive = {
                        coroutineScope.launch { sheetState.hide() }
                        onSubmitResetKey()
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
                    title = stringResource(id = R.string.key_generator_title),
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = GmaylTheme.color.mist10)
                    .padding(innerPadding),
            ) {
                when {
                    viewState.fetching -> KeyGeneratorShimmer()
                    else -> KeyGeneratorScreenContent(
                        viewState = viewState,
                        onClickCopyPrivateKey = onClickCopyPrivateKey,
                        onClickCopyPublicKey = onClickCopyPublicKey,
                        onClickGenerateNewKey = onClickGenerateNewKey,
                        onClickResetKey = onClickResetKey,
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyGeneratorShimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(CircleShape)
                .background(brush = gmaylShimmerBrush(), shape = CircleShape),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(CircleShape)
                .background(brush = gmaylShimmerBrush(), shape = CircleShape),
        )
    }
}

@Composable
private fun KeyGeneratorScreenContent(
    viewState: KeyGeneratorViewState,
    onClickCopyPrivateKey: () -> Unit,
    onClickCopyPublicKey: () -> Unit,
    onClickGenerateNewKey: () -> Unit,
    onClickResetKey: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GmaylCopyableText(
            value = TextFieldValue(viewState.privateKey.orEmpty()),
            enabled = !viewState.loading,
            title = stringResource(id = R.string.key_generator_private_key),
            hint = stringResource(id = R.string.key_generator_private_key_hint),
            onClick = onClickCopyPrivateKey,
        )
        GmaylCopyableText(
            value = TextFieldValue(viewState.publicKey.orEmpty()),
            enabled = !viewState.loading,
            title = stringResource(id = R.string.key_generator_public_key),
            hint = stringResource(id = R.string.key_generator_public_key_hint),
            onClick = onClickCopyPublicKey,
        )
        GmaylPrimaryButton(
            label = stringResource(id = R.string.key_generator_generate_new_key),
            loading = viewState.loading,
            enabled = !viewState.loading,
            onClick = onClickGenerateNewKey,
        )
        GmaylSecondaryButton(
            label = stringResource(id = R.string.key_generator_generate_reset_key),
            enabled = !viewState.loading,
            onClick = onClickResetKey,
        )
    }
}

@Preview
@Composable
private fun PreviewKeyGeneratorScreen(
    modifier: Modifier = Modifier,
) {
    KeyGeneratorScreen(
        viewState = KeyGeneratorViewState(),
        onClickBack = {},
        onClickCopyPrivateKey = {},
        onClickCopyPublicKey = {},
        onClickGenerateNewKey = {},
        onClickResetKey = {},
        onDismissDialog = {},
        onDismissSnackBar = {},
        onSubmitResetKey = {},
    )
}
