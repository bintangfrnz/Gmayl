package com.bintangfajarianto.gmayl.domain.usecase.auth

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepository

class GetUserUseCase(
    private val repository: AuthRepository,
) : BaseUseCase<Unit, GetUserUseCaseActionResult> {
    override suspend fun invoke(param: Unit): GetUserUseCaseActionResult =
        when (val result = repository.getUser()) {
            is Success -> GetUserUseCaseActionResult.Success(result.value)
            is Error -> GetUserUseCaseActionResult.Failed
        }

}

sealed class GetUserUseCaseActionResult : ActionResult {
    data class Success(val user: User) : GetUserUseCaseActionResult()
    object Failed : GetUserUseCaseActionResult()
}
