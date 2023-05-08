package com.bintangfajarianto.gmayl.feature.vm.home.sendmail

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.domain.di.home.SendMailUseCaseModule
import com.bintangfajarianto.gmayl.feature.constant.FeatureHomeConstants
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val SendMailViewModelModule: DI.Module
    get() = DI.Module(name = FeatureHomeConstants.SEND_MAIL_VIEW_MODEL_MODULE) {
        importOnce(SendMailUseCaseModule)

        bindProvider {
            SendMailViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                routeDestinationHandler = instance(),
                sendMailUseCase = instance(),
            )
        }
    }
