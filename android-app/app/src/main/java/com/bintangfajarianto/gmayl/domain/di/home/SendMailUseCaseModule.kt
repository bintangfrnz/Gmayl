package com.bintangfajarianto.gmayl.domain.di.home

import com.bintangfajarianto.gmayl.data.di.DataHomeModule
import com.bintangfajarianto.gmayl.domain.constant.DomainHomeConstant
import com.bintangfajarianto.gmayl.domain.usecase.home.SendMailUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val SendMailUseCaseModule: DI.Module
    get() = DI.Module(name = DomainHomeConstant.SEND_MAIL_USE_CASE) {
        importOnce(DataHomeModule)

        bindSingleton {
            SendMailUseCase(repository = instance())
        }
    }
