package com.bintangfajarianto.gmayl.di.module

import com.bintangfajarianto.gmayl.data.python.BystarBlockCipher
import com.bintangfajarianto.gmayl.di.constant.AppConstants
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val BystarBlockCipherProviderModule: DI.Module
    get() = DI.Module(name = "BystarBlockCipherProviderModule") {
        bindSingleton {
            BystarBlockCipher(
                context = instance(tag = AppConstants.APPLICATION_CONTEXT),
            )
        }
    }
