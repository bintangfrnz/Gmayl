package com.bintangfajarianto.gmayl.feature.vm.home.detailmail

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.home.Mail

data class MailDetailViewState(
    val navigateTo: RouteDestination? = null,
    val loading: Boolean = false,
    val dialogData: DialogData? = null,
) : ViewState

sealed class MailDetailAction : Action {
    object OnClickDeleteMail : MailDetailAction()
    data class OnClickReplyMail(val mail: Mail) : MailDetailAction()
    object OnDismissDialog : MailDetailAction()
    data class OnSubmitDeleteMail(val mail: Mail, val isInboxMail: Boolean) : MailDetailAction()
}

sealed class MailDetailActionResult : ActionResult {
    object BackToHome : MailDetailActionResult()
    object DismissDialog : MailDetailActionResult()
    data class NavigateToSendMail(val mail: Mail) : MailDetailActionResult()
    object ShowDialog : MailDetailActionResult()
    object ShowLoading : MailDetailActionResult()
}
