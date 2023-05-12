package com.bintangfajarianto.gmayl.feature.vm.home.detailmail

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.domain.di.crypto.VerifyMailUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.home.DecryptMailUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.home.DeleteMailUseCaseModule
import com.bintangfajarianto.gmayl.feature.constant.FeatureHomeConstants
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val MailDetailViewModelModule: DI.Module
    get() = DI.Module(name = FeatureHomeConstants.MAIL_DETAIL_VIEW_MODEL_MODULE) {
        importOnce(DecryptMailUseCaseModule)
        importOnce(DeleteMailUseCaseModule)
        importOnce(VerifyMailUseCaseModule)

        bindProvider {
            MailDetailViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                routeDestinationHandler = instance(),
                decryptMailUseCase = instance(),
                deleteMailUseCase = instance(),
                verifyMailUseCase = instance(),
            )
        }
    }
