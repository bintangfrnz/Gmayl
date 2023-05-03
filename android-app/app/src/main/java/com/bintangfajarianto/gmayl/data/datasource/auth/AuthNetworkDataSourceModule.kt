package com.bintangfajarianto.gmayl.data.datasource.auth

import com.bintangfajarianto.gmayl.data.constant.DataAuthConstant
import com.bintangfajarianto.gmayl.data.repository.auth.AuthNetworkRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val AuthNetworkDataSourceModule: DI.Module
    get() = DI.Module(name = DataAuthConstant.AUTH_NETWORK_DATA_SOURCE_MODULE) {

        bindSingleton<AuthNetworkRepository> {
            AuthNetworkDataSource()
        }
    }
