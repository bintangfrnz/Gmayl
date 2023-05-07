package com.bintangfajarianto.gmayl.feature.di

import com.bintangfajarianto.gmayl.feature.constant.FeatureHomeConstants
import com.bintangfajarianto.gmayl.feature.vm.home.HomeViewModelModule
import org.kodein.di.DI

val FeatureHomeModule: DI.Module
    get() = DI.Module(name = FeatureHomeConstants.MODULE) {
        importOnce(HomeViewModelModule)
    }
