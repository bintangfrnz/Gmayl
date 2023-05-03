package com.bintangfajarianto.gmayl.data.datasource.auth

import com.bintangfajarianto.gmayl.data.constant.DataAuthConstant
import com.bintangfajarianto.gmayl.data.constant.StorageConstant
import com.bintangfajarianto.gmayl.data.repository.auth.AuthStorageRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val AuthStorageDataSourceModule: DI.Module
    get() = DI.Module(name = DataAuthConstant.AUTH_STORAGE_DATA_SOURCE_MODULE) {

        bindSingleton<AuthStorageRepository> {
            AuthStorageDataSource(
                secureStorage = instance(tag = StorageConstant.SECURE_STORAGE),
            )
        }
    }
