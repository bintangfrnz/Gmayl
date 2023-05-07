package com.bintangfajarianto.gmayl.data.di

import com.bintangfajarianto.gmayl.data.constant.DataAuthConstant
import com.bintangfajarianto.gmayl.data.datasource.auth.AuthNetworkDataSourceModule
import com.bintangfajarianto.gmayl.data.datasource.auth.AuthStorageDataSourceModule
import com.bintangfajarianto.gmayl.data.repository.auth.AuthNetworkRepository
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepository
import com.bintangfajarianto.gmayl.data.repository.auth.AuthRepositoryImpl
import com.bintangfajarianto.gmayl.data.repository.auth.AuthStorageRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DataAuthModule: DI.Module
    get() = DI.Module(name = DataAuthConstant.MODULE) {
        importOnce(AuthStorageDataSourceModule)
        importOnce(AuthNetworkDataSourceModule)

        bindSingleton<AuthRepository> {
            AuthRepositoryImpl(
                networkRepository = instance<AuthNetworkRepository>(),
                storageRepository = instance<AuthStorageRepository>(),
            )
        }
    }
