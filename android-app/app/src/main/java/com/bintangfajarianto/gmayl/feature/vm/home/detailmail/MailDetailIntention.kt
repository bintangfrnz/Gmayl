package com.bintangfajarianto.gmayl.feature.vm.home.detailmail

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.home.Mail

data class MailDetailViewState(
    val decryptedMessage: String? = null,
    val verifiedMail: Boolean? = null,
    val errorMessageSymmetricKey: String? = null,
    val validSymmetricKey: Boolean = false,
    val loading: Boolean = false,
    val navigateTo: RouteDestination? = null,
    val showDecryptionDialog: Boolean = false,
    val showDigitalSignDialog: Boolean = false,
    val dialogData: DialogData? = null,
    val dataMsgCondition: DataMessageCondition? = null,
) : ViewState

sealed class MailDetailAction : Action {
    object OnClickDeleteMail : MailDetailAction()
    object OnClickDecryptMail : MailDetailAction()
    object OnClickVerifyMail : MailDetailAction()
    data class OnClickReplyMail(val mail: Mail) : MailDetailAction()
    object OnClickNavigateToKeyGenerator : MailDetailAction()
    object OnDismissDialog : MailDetailAction()
    object OnDismissSnackBar : MailDetailAction()
    data class OnInputSymmetricKey(val key: String) : MailDetailAction()
    data class OnSubmitDecryptMail(val hexBody: String, val key: String) : MailDetailAction()
    data class OnSubmitDeleteMail(val mail: Mail, val isInboxMail: Boolean) : MailDetailAction()
    data class OnSubmitVerifyMail(
        val plainBody: String,
        val publicKey: String,
        val r: String,
        val s: String,
    ) : MailDetailAction()
    data class OnReceiveDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : MailDetailAction()
}

sealed class MailDetailActionResult : ActionResult {
    object BackToHome : MailDetailActionResult()
    object DismissDialog : MailDetailActionResult()
    object NavigateToKeyGenerator : MailDetailActionResult()
    data class NavigateToSendMail(val mail: Mail) : MailDetailActionResult()
    data class SetDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : MailDetailActionResult()
    data class SetDecryptedMessage(val decryptedMessage: String?) : MailDetailActionResult()
    data class SetVerifiedMail(val verifiedMail: Boolean?) : MailDetailActionResult()
    data class SetErrorMessageSymmetricKey(val errorMsg: String?) : MailDetailActionResult()
    data class SetValidSymmetricKey(val isValid: Boolean) : MailDetailActionResult()
    object ShowDeleteDialog : MailDetailActionResult()
    object ShowDecryptionDialog : MailDetailActionResult()
    object ShowDigitalSignDialog : MailDetailActionResult()
    object ShowLoading : MailDetailActionResult()
}
