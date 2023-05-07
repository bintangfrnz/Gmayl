package com.bintangfajarianto.gmayl.feature.vm.home

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.router.AppRouter
import com.bintangfajarianto.gmayl.core.router.HomeRouter
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.domain.usecase.auth.GetUserUseCase
import com.bintangfajarianto.gmayl.domain.usecase.auth.GetUserUseCaseActionResult
import com.bintangfajarianto.gmayl.domain.usecase.home.GetInboxMailsUseCase
import com.bintangfajarianto.gmayl.domain.usecase.home.GetSentMailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class HomeViewModel(
    coroutineScope: CoroutineScope,
    routeDestinationHandler: RouteDestinationHandler,
    private val getInboxMailsUseCase: GetInboxMailsUseCase,
    private val getSentMailsUseCase: GetSentMailsUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel<HomeViewState, HomeAction, HomeActionResult>(
    initialState = HomeViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    private var tempSelectedDrawerItem: DrawerItemType = DrawerItemType.INBOX

    override suspend fun handleOnAction(action: HomeAction): HomeActionResult =
        when (action) {
            HomeAction.InitData -> {
                callEffect {
                    delay(2000L)
                    fetchMails(tempSelectedDrawerItem)
                }
                HomeActionResult.ShowLoading
            }
            HomeAction.OnClickBack -> HomeActionResult.ShowDialog
            HomeAction.OnClickLogout -> HomeActionResult.Logout
            is HomeAction.OnClickMailItem -> HomeActionResult.NavigateToDetailMail(action.mail)
            HomeAction.OnClickSendMail -> HomeActionResult.NavigateToSendMail
            HomeAction.OnDismissDialog -> HomeActionResult.DismissDialog
            HomeAction.OnDismissSnackBar -> HomeActionResult.SetDataCondition(null)
            is HomeAction.OnReceiveDataMsgCondition -> HomeActionResult.SetDataCondition(action.dataMsgCondition)
            is HomeAction.OnSelectDrawerItem -> {
                tempSelectedDrawerItem = action.drawerItem
                handleOnAction(HomeAction.InitData)
                HomeActionResult.SetSelectedDrawerItem(tempSelectedDrawerItem)
            }
        }

    private suspend fun fetchMails(selectedDrawerItem: DrawerItemType): HomeActionResult =
        when (selectedDrawerItem) {
            DrawerItemType.INBOX ->
                HomeActionResult.SetMailItems(getInboxMailsUseCase.invoke(Unit).inboxMails)
            DrawerItemType.SENT ->
                HomeActionResult.SetMailItems(getSentMailsUseCase.invoke(Unit).sentMails)
            else -> HomeActionResult.DismissDialog // Do Nothing
        }

    internal suspend fun getUser(): User =
        when (val result = getUserUseCase.invoke(Unit)) {
            is GetUserUseCaseActionResult.Success -> result.user
            is GetUserUseCaseActionResult.Failed -> User()
        }

    override suspend fun reducer(
        oldState: HomeViewState,
        actionResult: HomeActionResult,
    ): HomeViewState = HomeViewState(
        isInit = true,
        mailItems = oldState.mailItemsReducer(actionResult),
        selectedDrawerItem = oldState.selectedDrawerItemReducer(actionResult),
        navigateTo = shouldNavigateTo(actionResult),
        loading = loadingReducer(actionResult),
        dialogData = dialogDataReducer(actionResult),
        dataMsgCondition = oldState.dataConditionReducer(actionResult),
    )

    private fun HomeViewState.mailItemsReducer(actionResult: HomeActionResult): List<Mail> =
        when (actionResult) {
            is HomeActionResult.SetMailItems -> actionResult.mailItems
            else -> mailItems
        }

    private fun HomeViewState.selectedDrawerItemReducer(actionResult: HomeActionResult): DrawerItemType =
        when (actionResult) {
            is HomeActionResult.SetSelectedDrawerItem -> actionResult.drawerItem
            else -> selectedDrawerItem
        }

    override fun navigateToReducer(actionResult: HomeActionResult): RouteDestination? =
        when (actionResult) {
            HomeActionResult.Logout -> AppRouter.Logout
            HomeActionResult.NavigateToSendMail -> HomeRouter.SendMailPage
            is HomeActionResult.NavigateToDetailMail -> HomeRouter.DetailMailPage(actionResult.mail)
            else -> null
        }

    private fun loadingReducer(actionResult: HomeActionResult): Boolean =
        when (actionResult) {
            HomeActionResult.ShowLoading -> true
            else -> false
        }

    private fun dialogDataReducer(actionResult: HomeActionResult): DialogData? =
        when (actionResult) {
            HomeActionResult.ShowDialog -> DialogData(
                titleText = "Exit App",
                descriptionText = "Do you really want to quit <b>Gmayl</b>?",
                positiveButtonText = "Yes, Quit",
                negativeButtonText = "Stay Here",
            )
            else -> null
        }

    private fun HomeViewState.dataConditionReducer(actionResult: HomeActionResult): DataMessageCondition? {
        return when (actionResult) {
            is HomeActionResult.SetDataCondition -> actionResult.dataMsgCondition
            else -> dataMsgCondition
        }
    }
}
