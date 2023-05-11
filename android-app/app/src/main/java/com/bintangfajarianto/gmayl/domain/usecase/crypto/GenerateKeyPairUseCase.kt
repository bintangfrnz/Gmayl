package com.bintangfajarianto.gmayl.domain.usecase.crypto

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository

class GenerateKeyPairUseCase(private val repository: CryptoRepository) :
    BaseUseCase<Unit, GenerateKeyPairUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Unit): GenerateKeyPairUseCaseActionResultSuccess {
        val result = repository.generateKeyPair()
        repository.saveKeyPair(result)
        return GenerateKeyPairUseCaseActionResultSuccess(result)
    }
}

data class GenerateKeyPairUseCaseActionResultSuccess(val key: Pair<String, String>) : ActionResult
