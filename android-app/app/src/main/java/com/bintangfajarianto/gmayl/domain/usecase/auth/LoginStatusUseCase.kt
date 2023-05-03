package com.bintangfajarianto.gmayl.domain.usecase.auth

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepository

class LoginStatusUseCase(private val repository: AuthRepository) :
    BaseUseCase<Unit, LoginStatusUseCaseActionResult> {

    override suspend fun invoke(param: Unit): LoginStatusUseCaseActionResult =
        LoginStatusUseCaseActionResult(isLogin = repository.isLogin())
}

data class LoginStatusUseCaseActionResult(val isLogin: Boolean) : ActionResult
