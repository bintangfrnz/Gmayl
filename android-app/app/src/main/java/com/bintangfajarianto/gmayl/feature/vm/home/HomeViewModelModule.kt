package com.bintangfajarianto.gmayl.feature.vm.home

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.domain.di.auth.GetUserUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.home.GetInboxMailsUseCase
import com.bintangfajarianto.gmayl.domain.di.home.GetSentMailsUseCase
import com.bintangfajarianto.gmayl.domain.usecase.auth.GetUserUseCase
import com.bintangfajarianto.gmayl.domain.usecase.home.GetInboxMailsUseCase
import com.bintangfajarianto.gmayl.domain.usecase.home.GetSentMailsUseCase
import com.bintangfajarianto.gmayl.feature.constant.FeatureHomeConstants
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val HomeViewModelModule: DI.Module
    get() = DI.Module(name = FeatureHomeConstants.Home_VIEW_MODEL_MODULE) {
        importOnce(GetInboxMailsUseCase)
        importOnce(GetSentMailsUseCase)
        importOnce(GetUserUseCaseModule)

        bindProvider<HomeViewModel> {
            HomeViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                routeDestinationHandler = instance<RouteDestinationHandler>(),
                getInboxMailsUseCase = instance<GetInboxMailsUseCase>(),
                getSentMailsUseCase = instance<GetSentMailsUseCase>(),
                getUserUseCase = instance<GetUserUseCase>(),
            )
        }
    }
