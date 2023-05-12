package com.bintangfajarianto.gmayl.feature.vm.home.sendmail

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.router.CryptoRouter
import com.bintangfajarianto.gmayl.core.router.HomeRouter
import com.bintangfajarianto.gmayl.core.validator.EmailValidator
import com.bintangfajarianto.gmayl.core.validator.EmailValidatorActionResult
import com.bintangfajarianto.gmayl.core.validator.SymmetricKeyValidator
import com.bintangfajarianto.gmayl.core.validator.SymmetricKeyValidatorActionResult
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.domain.usecase.home.SendMailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class SendMailViewModel(
    coroutineScope: CoroutineScope,
    routeDestinationHandler: RouteDestinationHandler,
    private val sendMailUseCase: SendMailUseCase,
) : BaseViewModel<SendMailViewState, SendMailAction, SendMailActionResult>(
    initialState = SendMailViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    override suspend fun handleOnAction(action: SendMailAction): SendMailActionResult =
        when (action) {
            is SendMailAction.InitSendToEmail -> handleInputSendToEmail(action.email)
            SendMailAction.OnClickEncryptMail -> SendMailActionResult.ShowEncryptionDialog
            SendMailAction.OnClickSignMail -> SendMailActionResult.ShowDigitalSignDialog
            is SendMailAction.OnClickSendMail -> {
                callEffect {
                    delay(1000L)
                    sendMail(action.mail, action.privateKey, action.symmetricKey)
                }
                SendMailActionResult.ShowLoading
            }
            SendMailAction.OnClickNavigateToKeyGenerator -> SendMailActionResult.NavigateToKeyGenerator
            SendMailAction.OnDismissDialog -> SendMailActionResult.DismissDialog
            SendMailAction.OnDismissSnackBar -> SendMailActionResult.SetDataCondition(null)
            is SendMailAction.OnInputSendToEmail -> handleInputSendToEmail(action.email)
            is SendMailAction.OnInputSymmetricKey -> handleInputSymmetricKey(key = action.key)
            is SendMailAction.OnReceiveDataCondition -> SendMailActionResult.SetDataCondition(action.dataMsgCondition)
        }

    private suspend fun sendMail(mail: Mail, privateKey: String, symmetricKey: String): SendMailActionResult {
        sendMailUseCase.invoke(SendMailUseCase.Params(mail, privateKey, symmetricKey))
        return SendMailActionResult.NavigateToHome
    }

    private fun handleInputSendToEmail(email: String): SendMailActionResult =
        when (EmailValidator.validate(email)) {
            EmailValidatorActionResult.Valid ->
                SendMailActionResult.SetValidSendToEmail(isValid = true)
            EmailValidatorActionResult.InvalidFormat ->
                SendMailActionResult.SetErrorMessageSendToEmail(errorMsg = "Email format is invalid")
            EmailValidatorActionResult.Blank ->
                SendMailActionResult.SetErrorMessageSendToEmail(errorMsg = null)
        }

    private fun handleInputSymmetricKey(key: String): SendMailActionResult =
        when (SymmetricKeyValidator.validate(key)) {
            SymmetricKeyValidatorActionResult.Valid ->
                SendMailActionResult.SetValidSymmetricKey(isValid = true)
            SymmetricKeyValidatorActionResult.InvalidLength ->
                SendMailActionResult.SetErrorMessageSymmetricKey(errorMsg = "Symmetric key must be 16 characters")
            SymmetricKeyValidatorActionResult.Blank ->
                SendMailActionResult.SetValidSymmetricKey(isValid = true)
        }

    override suspend fun reducer(
        oldState: SendMailViewState,
        actionResult: SendMailActionResult,
    ): SendMailViewState = SendMailViewState(
        isInitSendToEmail = true,
        errorMessageSendToEmail = oldState.errorMessageSendToEmailReducer(actionResult),
        errorMessageSymmetricKey = oldState.errorMessageSymmetricKeyReducer(actionResult),
        validSendToEmail = oldState.validSentToEmailReducer(actionResult),
        validSymmetricKey = oldState.validSymmetricKeyReducer(actionResult),
        loading = loadingReducer(actionResult),
        navigateTo = shouldNavigateTo(actionResult),
        showDigitalSignDialog = showDigitalSignDialogReducer(actionResult),
        showEncryptionDialog = showEncryptionDialogReducer(actionResult),
        dataMsgCondition = oldState.dataConditionReducer(actionResult),
    )

    override fun navigateToReducer(actionResult: SendMailActionResult): RouteDestination? =
        when (actionResult) {
            SendMailActionResult.NavigateToKeyGenerator -> CryptoRouter.KeyGeneratorPage
            SendMailActionResult.NavigateToHome -> HomeRouter.HomePage
            else -> null
        }

    private fun SendMailViewState.errorMessageSendToEmailReducer(actionResult: SendMailActionResult): String? =
        when (actionResult) {
            is SendMailActionResult.SetErrorMessageSendToEmail -> actionResult.errorMsg
            is SendMailActionResult.SetValidSendToEmail -> null
            else -> errorMessageSendToEmail
        }

    private fun SendMailViewState.errorMessageSymmetricKeyReducer(actionResult: SendMailActionResult): String? =
        when (actionResult) {
            is SendMailActionResult.SetErrorMessageSymmetricKey -> actionResult.errorMsg
            is SendMailActionResult.SetValidSymmetricKey -> null
            else -> errorMessageSymmetricKey
        }

    private fun SendMailViewState.validSentToEmailReducer(actionResult: SendMailActionResult): Boolean =
        when (actionResult) {
            is SendMailActionResult.SetValidSendToEmail -> true
            is SendMailActionResult.SetErrorMessageSendToEmail -> false
            else -> validSendToEmail
        }

    private fun SendMailViewState.validSymmetricKeyReducer(actionResult: SendMailActionResult): Boolean =
        when (actionResult) {
            is SendMailActionResult.SetValidSymmetricKey -> true
            is SendMailActionResult.SetErrorMessageSymmetricKey -> false
            else -> validSymmetricKey
        }

    private fun loadingReducer(actionResult: SendMailActionResult): Boolean =
        when (actionResult) {
            SendMailActionResult.ShowLoading -> true
            else -> false
        }

    private fun showDigitalSignDialogReducer(actionResult: SendMailActionResult): Boolean =
        when (actionResult) {
            SendMailActionResult.ShowDigitalSignDialog,
            SendMailActionResult.NavigateToKeyGenerator -> true
            else -> false
        }

    private fun showEncryptionDialogReducer(actionResult: SendMailActionResult): Boolean =
        when (actionResult) {
            is SendMailActionResult.SetValidSymmetricKey,
            is SendMailActionResult.SetErrorMessageSymmetricKey,
            SendMailActionResult.ShowEncryptionDialog -> true
            else -> false
        }

    private fun SendMailViewState.dataConditionReducer(actionResult: SendMailActionResult): DataMessageCondition? {
        return when (actionResult) {
            is SendMailActionResult.SetDataCondition -> actionResult.dataMsgCondition
            SendMailActionResult.DismissDialog -> dataMsgCondition
            else -> null
        }
    }
}
