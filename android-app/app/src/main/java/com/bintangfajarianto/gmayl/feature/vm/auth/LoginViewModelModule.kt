package com.bintangfajarianto.gmayl.feature.vm.auth

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.domain.di.auth.LoginUseCaseModule
import com.bintangfajarianto.gmayl.feature.constant.FeatureAuthConstants
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val LoginViewModelModule: DI.Module
    get() = DI.Module(name = FeatureAuthConstants.LOGIN_VIEW_MODEL_MODULE) {
        importOnce(LoginUseCaseModule)

        bindProvider {
            LoginViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                routeDestinationHandler = instance(),
                loginUseCase = instance(),
            )
        }
    }
