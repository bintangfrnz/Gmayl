package com.bintangfajarianto.gmayl.feature.vm.home.sendmail

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.home.Mail

data class SendMailViewState(
    val errorMessageSendToEmail: String? = null,
    val validSendToEmail: Boolean = false,
    val loading: Boolean = false,
    val navigateTo: RouteDestination? = null,
    val showEncryptionDialog: Boolean = false,
    val showDigitalSignDialog: Boolean = false,
    val dataMsgCondition: DataMessageCondition? = null,
) : ViewState

sealed class SendMailAction : Action {
    object OnClickEncryptMail : SendMailAction()
    object OnClickSignMail : SendMailAction()
    data class OnClickSendMail(
        val mail: Mail,
        val toEmail: String,
        val publicKey: String,
        val symmetricKey: String,
    ) : SendMailAction()
    object OnDismissDialog : SendMailAction()
    object OnDismissSnackBar : SendMailAction()
    data class OnReceiveDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : SendMailAction()
    data class OnInputSendToEmail(val email: String) : SendMailAction()
}

sealed class SendMailActionResult : ActionResult {
    object DismissDialog : SendMailActionResult()
    object NavigateToHome : SendMailActionResult()
    data class SetDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : SendMailActionResult()
    data class SetErrorMessageSendToEmail(val errorMsg: String?) : SendMailActionResult()
    data class SetValidEmail(val isValid: Boolean) : SendMailActionResult()
    object ShowEncryptionDialog : SendMailActionResult()
    object ShowDigitalSignDialog : SendMailActionResult()
    object ShowLoading : SendMailActionResult()
}
