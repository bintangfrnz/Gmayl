package com.bintangfajarianto.gmayl.di.module

import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val RouteDestinationHandlerProviderModule: DI.Module
    get() = DI.Module(name = "RouteDestinationHandlerProviderModule") {
        bindSingleton {
            RouteDestinationHandler()
        }
    }
