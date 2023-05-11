package com.bintangfajarianto.gmayl.domain.usecase.crypto

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository

class DeleteKeyPairUseCase(private val repository: CryptoRepository) :
    BaseUseCase<Unit, DeleteKeyPairUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Unit): DeleteKeyPairUseCaseActionResultSuccess {
        repository.deleteKeyPair()
        return DeleteKeyPairUseCaseActionResultSuccess
    }

}

object DeleteKeyPairUseCaseActionResultSuccess : ActionResult
