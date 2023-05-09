package com.bintangfajarianto.gmayl.domain.usecase.home

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.model.home.InboxMail
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepository

class GetInboxMailsUseCase(
    private val repository: HomeRepository,
) : BaseUseCase<Unit, GetInboxMailsUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Unit): GetInboxMailsUseCaseActionResultSuccess =
        GetInboxMailsUseCaseActionResultSuccess(inboxMails = repository.getInboxMails())
}

data class GetInboxMailsUseCaseActionResultSuccess(val inboxMails: List<InboxMail>) : ActionResult
