package com.bintangfajarianto.gmayl.feature.di

import com.bintangfajarianto.gmayl.feature.constant.FeatureAuthConstants
import com.bintangfajarianto.gmayl.feature.vm.auth.LoginViewModelModule
import org.kodein.di.DI

val FeatureAuthModule: DI.Module
    get() = DI.Module(name = FeatureAuthConstants.MODULE) {
        importOnce(LoginViewModelModule)
    }
