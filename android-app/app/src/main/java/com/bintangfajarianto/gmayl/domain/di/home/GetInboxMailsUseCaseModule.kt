package com.bintangfajarianto.gmayl.domain.di.home

import com.bintangfajarianto.gmayl.data.di.DataHomeModule
import com.bintangfajarianto.gmayl.domain.constant.DomainHomeConstant
import com.bintangfajarianto.gmayl.domain.usecase.home.GetInboxMailsUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val GetInboxMailsUseCase: DI.Module
    get() = DI.Module(name = DomainHomeConstant.GET_INBOX_MAILS_USE_CASE) {
        importOnce(DataHomeModule)

        bindSingleton<GetInboxMailsUseCase> {
            GetInboxMailsUseCase(repository = instance())
        }
    }
