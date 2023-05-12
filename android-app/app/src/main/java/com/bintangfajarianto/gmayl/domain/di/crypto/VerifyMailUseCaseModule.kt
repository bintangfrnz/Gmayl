package com.bintangfajarianto.gmayl.domain.di.crypto

import com.bintangfajarianto.gmayl.data.di.DataCryptoModule
import com.bintangfajarianto.gmayl.domain.constant.DomainHomeConstant
import com.bintangfajarianto.gmayl.domain.usecase.crypto.VerifyMailUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val VerifyMailUseCaseModule: DI.Module
    get() = DI.Module(name = DomainHomeConstant.VERIFY_MAIL_USE_CASE) {
        importOnce(DataCryptoModule)

        bindSingleton {
            VerifyMailUseCase(repository = instance())
        }
    }
