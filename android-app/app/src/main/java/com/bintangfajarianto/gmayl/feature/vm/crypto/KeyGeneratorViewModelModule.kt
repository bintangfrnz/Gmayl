package com.bintangfajarianto.gmayl.feature.vm.crypto

import com.bintangfajarianto.gmayl.core.AppDispatchers
import com.bintangfajarianto.gmayl.core.coroutineScope
import com.bintangfajarianto.gmayl.domain.di.crypto.DeleteKeyPairUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.crypto.GenerateKeyPairUseCaseModule
import com.bintangfajarianto.gmayl.domain.di.crypto.GetKeyPairUseCaseModule
import com.bintangfajarianto.gmayl.feature.constant.FeatureCryptoConstant
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val KeyGeneratorViewModelModule: DI.Module
    get() = DI.Module(name = FeatureCryptoConstant.KEY_GENERATOR_VIEW_MODEL_MODULE) {
        importOnce(DeleteKeyPairUseCaseModule)
        importOnce(GenerateKeyPairUseCaseModule)
        importOnce(GetKeyPairUseCaseModule)

        bindProvider {
            KeyGeneratorViewModel(
                coroutineScope = AppDispatchers.coroutineScope,
                deleteKeyPairUseCase = instance(),
                routeDestinationHandler = instance(),
                generateKeyPairUseCase = instance(),
                getKeyPairUseCase = instance(),
            )
        }
    }
