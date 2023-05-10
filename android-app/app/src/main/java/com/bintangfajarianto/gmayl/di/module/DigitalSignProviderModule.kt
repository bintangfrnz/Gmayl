package com.bintangfajarianto.gmayl.di.module

import com.bintangfajarianto.gmayl.data.python.DigitalSign
import com.bintangfajarianto.gmayl.di.constant.AppConstants
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DigitalSignProviderModule: DI.Module
    get() = DI.Module(name = "DigitalSignProviderModule") {
        bindSingleton {
            DigitalSign(
                context = instance(tag = AppConstants.APPLICATION_CONTEXT),
            )
        }
    }
