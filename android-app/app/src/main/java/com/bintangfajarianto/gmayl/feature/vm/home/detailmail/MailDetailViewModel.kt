package com.bintangfajarianto.gmayl.feature.vm.home.detailmail

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.router.CryptoRouter
import com.bintangfajarianto.gmayl.core.router.HomeRouter
import com.bintangfajarianto.gmayl.core.validator.SymmetricKeyValidator
import com.bintangfajarianto.gmayl.core.validator.SymmetricKeyValidatorActionResult
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.general.DialogImageType
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.domain.usecase.crypto.VerifyMailUseCase
import com.bintangfajarianto.gmayl.domain.usecase.crypto.VerifyMailUseCaseActionResult
import com.bintangfajarianto.gmayl.domain.usecase.home.DecryptMailUseCase
import com.bintangfajarianto.gmayl.domain.usecase.home.DecryptMailUseCaseActionResult
import com.bintangfajarianto.gmayl.domain.usecase.home.DeleteMailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class MailDetailViewModel(
    coroutineScope: CoroutineScope,
    routeDestinationHandler: RouteDestinationHandler,
    private val decryptMailUseCase: DecryptMailUseCase,
    private val deleteMailUseCase: DeleteMailUseCase,
    private val verifyMailUseCase: VerifyMailUseCase,
) : BaseViewModel<MailDetailViewState, MailDetailAction, MailDetailActionResult>(
    initialState = MailDetailViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    override suspend fun handleOnAction(action: MailDetailAction): MailDetailActionResult =
        when (action) {
            MailDetailAction.OnClickDeleteMail -> MailDetailActionResult.ShowDeleteDialog
            MailDetailAction.OnClickDecryptMail -> MailDetailActionResult.ShowDecryptionDialog
            MailDetailAction.OnClickVerifyMail -> MailDetailActionResult.ShowDigitalSignDialog
            is MailDetailAction.OnClickReplyMail -> MailDetailActionResult.NavigateToSendMail(action.mail)
            MailDetailAction.OnClickNavigateToKeyGenerator -> MailDetailActionResult.NavigateToKeyGenerator
            MailDetailAction.OnDismissDialog -> MailDetailActionResult.DismissDialog
            MailDetailAction.OnDismissSnackBar -> MailDetailActionResult.SetDataCondition(null)
            is MailDetailAction.OnInputSymmetricKey -> handleInputSymmetricKey(action.key)
            is MailDetailAction.OnSubmitDecryptMail -> {
                callEffect {
                    delay(1000L)
                    decryptMail(action.hexBody, action.key)
                }
                MailDetailActionResult.ShowLoading
            }
            is MailDetailAction.OnSubmitDeleteMail -> {
                callEffect {
                    delay(1500L)
                    deleteMail(action.mail, action.isInboxMail)
                }
                MailDetailActionResult.ShowLoading
            }
            is MailDetailAction.OnSubmitVerifyMail -> {
                callEffect {
                    delay(1000L)
                    verifyMail(action.plainBody, action.publicKey, action.r, action.s)
                }
                MailDetailActionResult.ShowLoading
            }
            is MailDetailAction.OnReceiveDataCondition -> MailDetailActionResult.SetDataCondition(action.dataMsgCondition)
        }

    private fun handleInputSymmetricKey(key: String): MailDetailActionResult =
        when (SymmetricKeyValidator.validate(key)) {
            SymmetricKeyValidatorActionResult.Valid ->
                MailDetailActionResult.SetValidSymmetricKey(isValid = true)
            SymmetricKeyValidatorActionResult.InvalidLength ->
                MailDetailActionResult.SetErrorMessageSymmetricKey(errorMsg = "Symmetric key must be 16 characters")
            SymmetricKeyValidatorActionResult.Blank ->
                MailDetailActionResult.SetErrorMessageSymmetricKey(errorMsg = null)
        }

    private suspend fun decryptMail(hexBody: String, key: String): MailDetailActionResult =
        when (val result = decryptMailUseCase.invoke(DecryptMailUseCase.Params(hexBody, key))) {
            is DecryptMailUseCaseActionResult.Success -> MailDetailActionResult.SetDecryptedMessage(result.decrypted)
            DecryptMailUseCaseActionResult.Invalid -> MailDetailActionResult.SetDecryptedMessage(null)
        }

    private suspend fun deleteMail(mail: Mail, isInboxMail: Boolean): MailDetailActionResult {
        deleteMailUseCase.invoke(DeleteMailUseCase.Params(mail, isInboxMail))
        return MailDetailActionResult.BackToHome
    }

    private suspend fun verifyMail(
        plainBody: String,
        key: String,
        r: String,
        s: String,
    ): MailDetailActionResult =
        when (val result = verifyMailUseCase.invoke(VerifyMailUseCase.Params(plainBody, key, r, s))) {
            is VerifyMailUseCaseActionResult.Success -> MailDetailActionResult.SetVerifiedMail(result.verified)
            VerifyMailUseCaseActionResult.Invalid -> MailDetailActionResult.SetVerifiedMail(null)
        }

    override suspend fun reducer(
        oldState: MailDetailViewState,
        actionResult: MailDetailActionResult,
    ): MailDetailViewState = MailDetailViewState(
        decryptedMessage = oldState.decryptedMessageReducer(actionResult),
        verifiedMail = oldState.verifiedMailReducer(actionResult),
        errorMessageSymmetricKey = oldState.errorMessageSymmetricKeyReducer(actionResult),
        validSymmetricKey = oldState.validSymmetricKeyReducer(actionResult),
        navigateTo = shouldNavigateTo(actionResult),
        loading = oldState.loadingReducer(actionResult),
        showDecryptionDialog = oldState.showDecryptionDialogReducer(actionResult),
        showDigitalSignDialog = oldState.showDigitalSignDialogReducer(actionResult),
        dialogData = oldState.dialogDataReducer(actionResult),
        dataMsgCondition = oldState.dataConditionReducer(actionResult),
    )

    override fun navigateToReducer(actionResult: MailDetailActionResult): RouteDestination? =
        when (actionResult) {
            MailDetailActionResult.BackToHome -> HomeRouter.HomePage
            MailDetailActionResult.NavigateToKeyGenerator -> CryptoRouter.KeyGeneratorPage
            is MailDetailActionResult.NavigateToSendMail ->
                HomeRouter.SendMailPage(
                    sender = actionResult.mail.sender,
                    receiver = actionResult.mail.receiver,
                )
            else -> null
        }

    private fun MailDetailViewState.decryptedMessageReducer(actionResult: MailDetailActionResult): String? {
        return when (actionResult) {
            is MailDetailActionResult.SetDecryptedMessage -> actionResult.decryptedMessage
            else -> decryptedMessage
        }
    }

    private fun MailDetailViewState.verifiedMailReducer(actionResult: MailDetailActionResult): Boolean? {
        return when (actionResult) {
            is MailDetailActionResult.SetVerifiedMail -> actionResult.verifiedMail
            is MailDetailActionResult.SetDataCondition -> null
            else -> verifiedMail
        }
    }

    private fun MailDetailViewState.errorMessageSymmetricKeyReducer(actionResult: MailDetailActionResult): String? =
        when (actionResult) {
            is MailDetailActionResult.SetErrorMessageSymmetricKey -> actionResult.errorMsg
            is MailDetailActionResult.SetValidSymmetricKey -> null
            else -> errorMessageSymmetricKey
        }

    private fun MailDetailViewState.validSymmetricKeyReducer(actionResult: MailDetailActionResult): Boolean =
        when (actionResult) {
            is MailDetailActionResult.SetValidSymmetricKey -> true
            is MailDetailActionResult.SetErrorMessageSymmetricKey -> false
            else -> validSymmetricKey
        }

    private fun MailDetailViewState.loadingReducer(actionResult: MailDetailActionResult): Boolean =
        when (actionResult) {
            MailDetailActionResult.ShowLoading -> true
            is MailDetailActionResult.SetDecryptedMessage,
            is MailDetailActionResult.SetVerifiedMail,
            MailDetailActionResult.BackToHome -> false
            else -> loading
        }

    private fun MailDetailViewState.showDecryptionDialogReducer(actionResult: MailDetailActionResult): Boolean =
        when (actionResult) {
            is MailDetailActionResult.SetValidSymmetricKey,
            is MailDetailActionResult.SetErrorMessageSymmetricKey,
            MailDetailActionResult.ShowDecryptionDialog -> true
            MailDetailActionResult.ShowLoading -> showDecryptionDialog
            else -> false
        }

    private fun MailDetailViewState.showDigitalSignDialogReducer(actionResult: MailDetailActionResult): Boolean =
        when (actionResult) {
            MailDetailActionResult.NavigateToKeyGenerator,
            MailDetailActionResult.ShowDigitalSignDialog -> true
            MailDetailActionResult.ShowLoading -> showDigitalSignDialog
            else -> false
        }

    private fun MailDetailViewState.dialogDataReducer(actionResult: MailDetailActionResult): DialogData? =
        when (actionResult) {
            MailDetailActionResult.ShowDeleteDialog -> DialogData(
                imageType = DialogImageType.WARNING,
                titleText = "Delete Mail?",
                descriptionText = "This message will be deleted forever",
                positiveButtonText = "Yes, Delete",
                negativeButtonText = "Cancel",
            )
            MailDetailActionResult.ShowLoading -> dialogData
            else -> null
        }

    private fun MailDetailViewState.dataConditionReducer(actionResult: MailDetailActionResult): DataMessageCondition? {
        return when (actionResult) {
            is MailDetailActionResult.SetDataCondition -> actionResult.dataMsgCondition
            MailDetailActionResult.DismissDialog -> dataMsgCondition
            else -> null
        }
    }
}
