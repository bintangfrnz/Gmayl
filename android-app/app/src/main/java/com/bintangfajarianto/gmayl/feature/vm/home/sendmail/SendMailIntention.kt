package com.bintangfajarianto.gmayl.feature.vm.home.sendmail

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.home.Mail

data class SendMailViewState(
    val isInitSendToEmail: Boolean = false,
    val errorMessageSendToEmail: String? = null,
    val errorMessageSymmetricKey: String? = null,
    val validSendToEmail: Boolean = false,
    val validSymmetricKey: Boolean = false,
    val loading: Boolean = false,
    val navigateTo: RouteDestination? = null,
    val showEncryptionDialog: Boolean = false,
    val showDigitalSignDialog: Boolean = false,
    val dataMsgCondition: DataMessageCondition? = null,
) : ViewState

sealed class SendMailAction : Action {
    data class InitSendToEmail(val email: String) : SendMailAction()
    object OnClickEncryptMail : SendMailAction()
    object OnClickSignMail : SendMailAction()
    data class OnClickSendMail(
        val mail: Mail,
        val privateKey: String,
        val symmetricKey: String,
    ) : SendMailAction()
    object OnClickNavigateToKeyGenerator : SendMailAction()
    object OnDismissDialog : SendMailAction()
    object OnDismissSnackBar : SendMailAction()
    data class OnInputSendToEmail(val email: String) : SendMailAction()
    data class OnInputSymmetricKey(val key: String) : SendMailAction()
    data class OnReceiveDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : SendMailAction()
}

sealed class SendMailActionResult : ActionResult {
    object DismissDialog : SendMailActionResult()
    object NavigateToKeyGenerator : SendMailActionResult()
    object NavigateToHome : SendMailActionResult()
    data class SetDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : SendMailActionResult()
    data class SetErrorMessageSendToEmail(val errorMsg: String?) : SendMailActionResult()
    data class SetErrorMessageSymmetricKey(val errorMsg: String?) : SendMailActionResult()
    data class SetValidSendToEmail(val isValid: Boolean) : SendMailActionResult()
    data class SetValidSymmetricKey(val isValid: Boolean) : SendMailActionResult()
    object ShowEncryptionDialog : SendMailActionResult()
    object ShowDigitalSignDialog : SendMailActionResult()
    object ShowLoading : SendMailActionResult()
}
