package com.bintangfajarianto.gmayl.feature.vm.auth

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.router.AuthRouter
import com.bintangfajarianto.gmayl.core.router.HomeRouter
import com.bintangfajarianto.gmayl.core.validator.EmailValidator
import com.bintangfajarianto.gmayl.core.validator.EmailValidatorActionResult
import com.bintangfajarianto.gmayl.core.validator.PasswordValidator
import com.bintangfajarianto.gmayl.core.validator.PasswordValidatorActionResult
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.domain.usecase.auth.LoginUseCase
import com.bintangfajarianto.gmayl.domain.usecase.auth.LoginUseCaseActionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class LoginViewModel(
    coroutineScope: CoroutineScope,
    private val routeDestinationHandler: RouteDestinationHandler,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginViewState, LoginAction, LoginActionResult>(
    initialState = LoginViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    override suspend fun handleOnAction(action: LoginAction): LoginActionResult =
        when (action) {
            is LoginAction.OnClickLogin -> {
                callEffect {
                    delay(2000L)
                    login(email = action.email, password = action.password)
                }
                LoginActionResult.ShowLoading
            }
            LoginAction.OnDismissDialog -> LoginActionResult.DismissDialog
            is LoginAction.OnInputEmail -> handleInputEmail(email = action.email)
            is LoginAction.OnInputPassword -> handleInputPassword(password = action.password)
        }

    private suspend fun login(email: String, password: String): LoginActionResult =
        when (loginUseCase.invoke(LoginUseCase.Param(email, password))) {
            is LoginUseCaseActionResult.Success -> LoginActionResult.NavigateToHome
            is LoginUseCaseActionResult.Invalid -> LoginActionResult.ShowDialog
        }

    private fun handleInputEmail(email: String): LoginActionResult =
        when (EmailValidator.validate(email)) {
            EmailValidatorActionResult.Valid ->
                LoginActionResult.SetValidEmail(isValid = true)
            EmailValidatorActionResult.InvalidFormat ->
                LoginActionResult.SetErrorMessageEmail(errorMsg = "Email format is invalid")
            EmailValidatorActionResult.Blank ->
                LoginActionResult.SetErrorMessageEmail(errorMsg = null)
        }

    private fun handleInputPassword(password: String): LoginActionResult =
        when (PasswordValidator.validate(password)) {
            PasswordValidatorActionResult.Valid ->
                LoginActionResult.SetValidPassword(isValid = true)
            PasswordValidatorActionResult.InvalidLength ->
                LoginActionResult.SetErrorMessagePassword(errorMsg = "Password must be between 8 and 100 characters")
            PasswordValidatorActionResult.Blank ->
                LoginActionResult.SetErrorMessagePassword(errorMsg = null)
        }

    override suspend fun reducer(
        oldState: LoginViewState,
        actionResult: LoginActionResult,
    ): LoginViewState = LoginViewState(
        errorMessageEmail = oldState.errorMessageEmailReducer(actionResult),
        errorMessagePassword = oldState.errorMessagePasswordReducer(actionResult),
        validEmail = oldState.validEmailReducer(actionResult),
        validPassword = oldState.validPasswordReducer(actionResult),
        loading = loadingReducer(actionResult),
        dialogData = dialogDataReducer(actionResult),
        navigateTo = shouldNavigateTo(actionResult),
    )

    override fun navigateToReducer(actionResult: LoginActionResult): RouteDestination? =
        when (actionResult) {
            LoginActionResult.NavigateToHome -> {
                routeDestinationHandler.popUpToRoute = AuthRouter.LoginPage
                routeDestinationHandler.inclusive = true

                HomeRouter.HomePage
            }
            else -> null
        }

    private fun LoginViewState.errorMessageEmailReducer(actionResult: LoginActionResult): String? =
        when (actionResult) {
            is LoginActionResult.SetErrorMessageEmail -> actionResult.errorMsg
            is LoginActionResult.SetValidEmail -> null
            else -> errorMessageEmail
        }

    private fun LoginViewState.errorMessagePasswordReducer(actionResult: LoginActionResult): String? =
        when (actionResult) {
            is LoginActionResult.SetErrorMessagePassword -> actionResult.errorMsg
            is LoginActionResult.SetValidPassword -> null
            else -> errorMessagePassword
        }

    private fun LoginViewState.validEmailReducer(actionResult: LoginActionResult): Boolean =
        when (actionResult) {
            is LoginActionResult.SetValidEmail -> true
            is LoginActionResult.SetErrorMessageEmail -> false
            else -> validEmail
        }

    private fun LoginViewState.validPasswordReducer(actionResult: LoginActionResult): Boolean =
        when (actionResult) {
            is LoginActionResult.SetValidPassword -> true
            is LoginActionResult.SetErrorMessagePassword -> false
            else -> validPassword
        }

    private fun loadingReducer(actionResult: LoginActionResult): Boolean =
        when (actionResult) {
            LoginActionResult.ShowLoading -> true
            else -> false
        }

    private fun dialogDataReducer(actionResult: LoginActionResult): DialogData? =
        when (actionResult) {
            LoginActionResult.ShowDialog -> DialogData(
                titleText = "Login Failed",
                descriptionText = "Currently you can only login with email <b>admin@gmail.com</b> and password <b>admin123</b>",
                positiveButtonText = "Okay",
            )
            else -> null
        }
}
