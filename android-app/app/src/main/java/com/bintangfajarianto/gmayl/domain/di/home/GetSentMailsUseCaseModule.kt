package com.bintangfajarianto.gmayl.domain.di.home

import com.bintangfajarianto.gmayl.data.di.DataHomeModule
import com.bintangfajarianto.gmayl.domain.constant.DomainHomeConstant
import com.bintangfajarianto.gmayl.domain.usecase.home.GetSentMailsUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GetSentMailsUseCase: DI.Module
    get() = DI.Module(name = DomainHomeConstant.GET_SENT_MAILS_USE_CASE) {
        importOnce(DataHomeModule)

        bindSingleton<GetSentMailsUseCase> {
            GetSentMailsUseCase(repository = instance())
        }
    }
