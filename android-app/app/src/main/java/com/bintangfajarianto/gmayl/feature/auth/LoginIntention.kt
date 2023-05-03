package com.bintangfajarianto.gmayl.feature.auth

import com.bintangfajarianto.gmayl.base.Action
import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.ViewState
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.data.model.general.DialogData

data class LoginViewState(
    val errorMessageEmail: String? = null,
    val errorMessagePassword: String? = null,
    val validEmail: Boolean = false,
    val validPassword: Boolean = false,
    val loading: Boolean = false,
    val dialogData: DialogData? = null,
    val navigateTo: RouteDestination? = null,
) : ViewState {
    companion object {
        fun init() = LoginViewState()
    }
}

sealed class LoginAction : Action {
    data class OnClickLogin(val email: String, val password: String) : LoginAction()
    object OnDismissDialog : LoginAction()
    data class OnInputEmail(val email: String) : LoginAction()
    data class OnInputPassword(val password: String) : LoginAction()
}

sealed class LoginActionResult : ActionResult {
    object DismissDialog : LoginActionResult()
    object NavigateToHome : LoginActionResult()
    data class SetErrorMessageEmail(val errorMsg: String?) : LoginActionResult()
    data class SetErrorMessagePassword(val errorMsg: String?) : LoginActionResult()
    data class SetValidEmail(val isValid: Boolean) : LoginActionResult()
    data class SetValidPassword(val isValid: Boolean) : LoginActionResult()
    object ShowDialog : LoginActionResult()
    object ShowLoading : LoginActionResult()
}
