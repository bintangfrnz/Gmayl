package com.bintangfajarianto.gmayl.domain.di.home

import com.bintangfajarianto.gmayl.data.di.DataHomeModule
import com.bintangfajarianto.gmayl.domain.constant.DomainHomeConstant
import com.bintangfajarianto.gmayl.domain.usecase.home.DeleteMailUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DeleteMailUseCaseModule: DI.Module
    get() = DI.Module(name = DomainHomeConstant.DELETE_MAIL_USE_CASE) {
        importOnce(DataHomeModule)

        bindSingleton {
            DeleteMailUseCase(repository = instance())
        }
    }
