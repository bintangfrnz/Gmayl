package com.bintangfajarianto.gmayl.domain.usecase.auth

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepository

class LoginUseCase(private val repository: AuthRepository) :
    BaseUseCase<LoginUseCase.Param, LoginUseCaseActionResult> {

    override suspend fun invoke(param: Param): LoginUseCaseActionResult =
        when (val result = repository.login(param.email, param.password)) {
            is Success -> {
                repository.transformResponse(result.value)
                LoginUseCaseActionResult.Success
            }
            is Error -> LoginUseCaseActionResult.Invalid
        }

    data class Param(val email: String, val password: String)
}

sealed class LoginUseCaseActionResult : ActionResult {
    object Invalid : LoginUseCaseActionResult()
    object Success : LoginUseCaseActionResult()
}
