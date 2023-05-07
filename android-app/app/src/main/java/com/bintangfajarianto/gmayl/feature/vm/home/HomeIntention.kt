package com.bintangfajarianto.gmayl.feature.vm.home

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.data.model.home.Mail

data class HomeViewState(
    val isInit: Boolean = false,
    val mailItems: List<Mail> = emptyList(),
    val selectedDrawerItem: DrawerItemType = DrawerItemType.INBOX,
    val navigateTo: RouteDestination? = null,
    val loading: Boolean = false,
    val dialogData: DialogData? = null,
    val dataMsgCondition: DataMessageCondition? = null,
) : ViewState

sealed class HomeAction : Action {
    object InitData : HomeAction()
    object OnClickBack : HomeAction()
    object OnClickLogout : HomeAction()
    data class OnClickMailItem(val mail: Mail) : HomeAction()
    object OnClickSendMail : HomeAction()
    object OnDismissDialog : HomeAction()
    object OnDismissSnackBar : HomeAction()
    data class OnReceiveDataMsgCondition(val dataMsgCondition: DataMessageCondition?) : HomeAction()
    data class OnSelectDrawerItem(val drawerItem: DrawerItemType) : HomeAction()
}

sealed class HomeActionResult : ActionResult {
    object DismissDialog : HomeActionResult()
    object Logout : HomeActionResult()
    object NavigateToSendMail : HomeActionResult()
    data class NavigateToDetailMail(val mail: Mail) : HomeActionResult()
    data class SetDataCondition(val dataMsgCondition: DataMessageCondition?) : HomeActionResult()
    data class SetMailItems(val mailItems: List<Mail>) : HomeActionResult()
    data class SetSelectedDrawerItem(val drawerItem: DrawerItemType) : HomeActionResult()
    object ShowDialog : HomeActionResult()
    object ShowLoading : HomeActionResult()
}
