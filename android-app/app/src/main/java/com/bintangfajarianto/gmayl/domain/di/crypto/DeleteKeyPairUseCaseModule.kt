package com.bintangfajarianto.gmayl.domain.di.crypto

import com.bintangfajarianto.gmayl.data.di.DataCryptoModule
import com.bintangfajarianto.gmayl.domain.constant.DomainCryptoConstant
import com.bintangfajarianto.gmayl.domain.usecase.crypto.DeleteKeyPairUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DeleteKeyPairUseCaseModule: DI.Module
    get() = DI.Module(name = DomainCryptoConstant.DELETE_KEY_PAIR_USE_CASE_MODULE) {
        importOnce(DataCryptoModule)

        bindSingleton {
            DeleteKeyPairUseCase(repository = instance())
        }
    }
