package com.bintangfajarianto.gmayl.domain.usecase.crypto

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository
import java.math.BigInteger

class VerifyMailUseCase(
    private val repository: CryptoRepository,
) : BaseUseCase<VerifyMailUseCase.Params, VerifyMailUseCaseActionResult> {
    override suspend fun invoke(param: Params): VerifyMailUseCaseActionResult =
        when {
            param.publicKey.isBlank() -> VerifyMailUseCaseActionResult.Invalid
            else -> VerifyMailUseCaseActionResult.Success(
                verified = repository.verify(
                    message = param.plainBody,
                    publicKey = param.publicKey,
                    r = param.r,
                    s = param.s,
                )
            )
        }

    data class Params(
        val plainBody: String,
        val publicKey: String,
        val r: BigInteger,
        val s: BigInteger,
    )
}

sealed class VerifyMailUseCaseActionResult : ActionResult {
    data class Success(val verified: Boolean) : VerifyMailUseCaseActionResult()
    object Invalid : VerifyMailUseCaseActionResult()
}
