package com.bintangfajarianto.gmayl.domain.usecase.home

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepository

class SendMailUseCase(
    private val repository: HomeRepository,
) : BaseUseCase<SendMailUseCase.Params, SendMailUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Params): SendMailUseCaseActionResultSuccess {
        repository.sendMail(param.mail, param.toEmail, param.publicKey, param.symmetricKey)
        return SendMailUseCaseActionResultSuccess
    }

    data class Params(
        val mail: Mail,
        val toEmail: String,
        val publicKey: String,
        val symmetricKey: String,
    )

}

object SendMailUseCaseActionResultSuccess : ActionResult
