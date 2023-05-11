package com.bintangfajarianto.gmayl.feature.di

import com.bintangfajarianto.gmayl.feature.constant.FeatureCryptoConstant
import com.bintangfajarianto.gmayl.feature.vm.crypto.KeyGeneratorViewModelModule
import org.kodein.di.DI

val FeatureCryptoModule: DI.Module
    get() = DI.Module(name = FeatureCryptoConstant.MODULE) {
        importOnce(KeyGeneratorViewModelModule)
    }
