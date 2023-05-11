package com.bintangfajarianto.gmayl.domain.usecase.home

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository

class DecryptMailUseCase(
    private val repository: CryptoRepository,
) : BaseUseCase<DecryptMailUseCase.Params, DecryptMailUseCaseActionResult> {
    override suspend fun invoke(param: Params): DecryptMailUseCaseActionResult =
        when {
            param.symmetricKey.isBlank() -> DecryptMailUseCaseActionResult.Invalid
            else -> DecryptMailUseCaseActionResult.Success(
                decrypted = repository.decryptMail(
                    hexBody = param.hexBody,
                    symmetricKey = param.symmetricKey,
                )
            )
        }

    data class Params(
        val hexBody: String,
        val symmetricKey: String,
    )
}

sealed class DecryptMailUseCaseActionResult : ActionResult {
    data class Success(val decrypted: String?) : DecryptMailUseCaseActionResult()
    object Invalid : DecryptMailUseCaseActionResult()
}
