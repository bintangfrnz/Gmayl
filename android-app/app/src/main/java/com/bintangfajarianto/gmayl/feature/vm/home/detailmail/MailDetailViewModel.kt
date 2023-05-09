package com.bintangfajarianto.gmayl.feature.vm.home.detailmail

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.router.HomeRouter
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.general.DialogImageType
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.domain.usecase.home.DeleteMailUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class MailDetailViewModel(
    coroutineScope: CoroutineScope,
    routeDestinationHandler: RouteDestinationHandler,
    private val deleteMailUseCase: DeleteMailUseCase,
) : BaseViewModel<MailDetailViewState, MailDetailAction, MailDetailActionResult>(
    initialState = MailDetailViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    override suspend fun handleOnAction(action: MailDetailAction): MailDetailActionResult =
        when (action) {
            MailDetailAction.OnClickDeleteMail -> MailDetailActionResult.ShowDialog
            is MailDetailAction.OnClickReplyMail -> MailDetailActionResult.NavigateToSendMail(action.mail)
            MailDetailAction.OnDismissDialog -> MailDetailActionResult.DismissDialog
            is MailDetailAction.OnSubmitDeleteMail -> {
                callEffect {
                    delay(1500L)
                    deleteMail(action.mail, action.isInboxMail)
                }
                MailDetailActionResult.ShowLoading
            }
        }

    private suspend fun deleteMail(mail: Mail, isInboxMail: Boolean): MailDetailActionResult {
        deleteMailUseCase.invoke(DeleteMailUseCase.Params(mail, isInboxMail))
        return MailDetailActionResult.BackToHome
    }

    override suspend fun reducer(
        oldState: MailDetailViewState,
        actionResult: MailDetailActionResult,
    ): MailDetailViewState = MailDetailViewState(
        navigateTo = shouldNavigateTo(actionResult),
        loading = oldState.loadingReducer(actionResult),
        dialogData = oldState.dialogDataReducer(actionResult),
    )

    override fun navigateToReducer(actionResult: MailDetailActionResult): RouteDestination? =
        when (actionResult) {
            MailDetailActionResult.BackToHome -> HomeRouter.HomePage
            is MailDetailActionResult.NavigateToSendMail ->
                HomeRouter.SendMailPage(
                    sender = actionResult.mail.sender,
                    receiver = actionResult.mail.receiver,
                )
            else -> null
        }

    private fun MailDetailViewState.loadingReducer(actionResult: MailDetailActionResult): Boolean =
        when (actionResult) {
            MailDetailActionResult.ShowLoading -> true
            MailDetailActionResult.BackToHome -> false
            else -> loading
        }

    private fun MailDetailViewState.dialogDataReducer(actionResult: MailDetailActionResult): DialogData? =
        when (actionResult) {
            MailDetailActionResult.ShowDialog -> DialogData(
                imageType = DialogImageType.WARNING,
                titleText = "Delete Mail?",
                descriptionText = "This message will be deleted forever",
                positiveButtonText = "Yes, Delete",
                negativeButtonText = "Cancel",
            )
            MailDetailActionResult.ShowLoading -> dialogData
            else -> null
        }
}
