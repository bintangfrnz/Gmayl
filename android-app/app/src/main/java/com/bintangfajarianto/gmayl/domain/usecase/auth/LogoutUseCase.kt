package com.bintangfajarianto.gmayl.domain.usecase.auth

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository,
) : BaseUseCase<Unit, LogoutUseCaseActionResultSuccess> {

    override suspend fun invoke(param: Unit): LogoutUseCaseActionResultSuccess {
        repository.logout()
        return LogoutUseCaseActionResultSuccess
    }
}

object LogoutUseCaseActionResultSuccess : ActionResult
