package com.bintangfajarianto.gmayl.domain.usecase.crypto

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository

class GetKeyPairUseCase(private val repository: CryptoRepository) :
    BaseUseCase<Unit, GetKeyPairUseCaseActionResult> {
    override suspend fun invoke(param: Unit): GetKeyPairUseCaseActionResult =
        when (val result = repository.getKeyPair()) {
            is Success -> GetKeyPairUseCaseActionResult.Success(result.value)
            is Error -> GetKeyPairUseCaseActionResult.Failed
        }
}

sealed class GetKeyPairUseCaseActionResult : ActionResult {
    data class Success(val key: Pair<String, String>) : GetKeyPairUseCaseActionResult()
    object Failed : GetKeyPairUseCaseActionResult()
}
