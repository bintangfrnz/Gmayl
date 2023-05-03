package com.bintangfajarianto.gmayl.feature.auth

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.di.constant.AuthConstants
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val LoginViewModelModule: DI.Module
    get() = DI.Module(name = AuthConstants.LOGIN_VIEW_MODEL_MODULE) {


        bindProvider<LoginViewModel> {
            LoginViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                routeDestinationHandler = instance<RouteDestinationHandler>(),
            )
        }
    }
