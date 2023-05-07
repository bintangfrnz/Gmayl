package com.bintangfajarianto.gmayl.domain.usecase.home

import com.bintangfajarianto.gmayl.base.ActionResult
import com.bintangfajarianto.gmayl.base.BaseUseCase
import com.bintangfajarianto.gmayl.data.model.home.SentMail
import com.bintangfajarianto.gmayl.data.repository.home.HomeRepository

class GetSentMailsUseCase(
    private val repository: HomeRepository,
) : BaseUseCase<Unit, GetSentMailsUseCaseActionResultSuccess> {
    override suspend fun invoke(param: Unit): GetSentMailsUseCaseActionResultSuccess =
        GetSentMailsUseCaseActionResultSuccess(sentMails = repository.getSentMails())
}

data class GetSentMailsUseCaseActionResultSuccess(val sentMails: List<SentMail>) : ActionResult
