package com.bintangfajarianto.gmayl.feature.vm.crypto

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData

data class KeyGeneratorViewState(
    val isInitKeyPair: Boolean = false,
    val privateKey: String? = null,
    val publicKey: String? = null,
    val navigateTo: RouteDestination? = null,
    val fetching: Boolean = false,
    val loading: Boolean = true,
    val dialogData: DialogData? = null,
    val dataMsgCondition: DataMessageCondition? = null,
) : ViewState

sealed class KeyGeneratorAction : Action {
    object InitKeyPair : KeyGeneratorAction()
    object OnClickGenerateNewKey : KeyGeneratorAction()
    object OnClickResetKey : KeyGeneratorAction()
    object OnDismissDialog : KeyGeneratorAction()
    object OnDismissSnackBar : KeyGeneratorAction()
    data class OnReceiveDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : KeyGeneratorAction()
    object OnSubmitResetKey : KeyGeneratorAction()
}

sealed class KeyGeneratorActionResult : ActionResult {
    object DoNothing : KeyGeneratorActionResult()
    object DismissDialog : KeyGeneratorActionResult()
    data class SetDataCondition(
        val dataMsgCondition: DataMessageCondition?,
    ) : KeyGeneratorActionResult()
    data class SetKeyPair(val key: Pair<String?, String?>) : KeyGeneratorActionResult()
    object ShowDeleteDialog : KeyGeneratorActionResult()
    object ShowFetching : KeyGeneratorActionResult()
    object ShowLoading : KeyGeneratorActionResult()
}
