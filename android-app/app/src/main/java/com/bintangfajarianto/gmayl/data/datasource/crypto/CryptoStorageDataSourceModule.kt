package com.bintangfajarianto.gmayl.data.datasource.crypto

import com.bintangfajarianto.gmayl.data.constant.DataCryptoConstant
import com.bintangfajarianto.gmayl.data.constant.StorageConstant
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoStorageRepository
import com.bintangfajarianto.gmayl.di.module.MailDatabaseProviderModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val CryptoStorageDataSourceModule: DI.Module
    get() = DI.Module(name = DataCryptoConstant.STORAGE_DATA_SOURCE_MODULE) {
        importOnce(MailDatabaseProviderModule)

        bindSingleton<CryptoStorageRepository> {
            CryptoStorageDataSource(
                instance(tag = StorageConstant.SECURE_STORAGE),
            )
        }
    }
