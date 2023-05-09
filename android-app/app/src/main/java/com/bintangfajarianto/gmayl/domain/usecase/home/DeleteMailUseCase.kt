package com.bintangfajarianto.gmayl.domain.usecase.home

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepository

class DeleteMailUseCase(
    private val repository: HomeRepository,
) : BaseUseCase<DeleteMailUseCase.Params, DeleteMailUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Params): DeleteMailUseCaseActionResultSuccess {
        when {
            param.isInboxMail -> repository.deleteInboxMail(param.mail as InboxMail)
            else -> repository.deleteSentMail(param.mail as SentMail)
        }
        return DeleteMailUseCaseActionResultSuccess
    }

    data class Params(
        val mail: Mail,
        val isInboxMail: Boolean,
    )
}

object DeleteMailUseCaseActionResultSuccess : ActionResult
