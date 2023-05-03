package com.bintangfajarianto.gmayl.di.module

import com.bintangfajarianto.gmayl.di.constant.AuthConstants
import com.bintangfajarianto.gmayl.feature.auth.LoginViewModelModule
import org.kodein.di.DI

val AuthModule: DI.Module
    get() = DI.Module(name = AuthConstants.MODULE) {
        importOnce(LoginViewModelModule)
    }
