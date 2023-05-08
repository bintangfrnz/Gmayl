package com.bintangfajarianto.gmayl.domain.di.auth

import com.bintangfajarianto.gmayl.data.di.DataAuthModule
import com.bintangfajarianto.gmayl.domain.constant.DomainAuthConstant
import com.bintangfajarianto.gmayl.domain.usecase.auth.LogoutUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val LogoutUseCaseModule: DI.Module
    get() = DI.Module(name = DomainAuthConstant.LOGOUT_USE_CASE_MODULE) {
        importOnce(DataAuthModule)

        bindSingleton {
            LogoutUseCase(repository = instance())
        }
    }
