package com.bintangfajarianto.gmayl.data.di

import com.bintangfajarianto.gmayl.data.constant.DataCryptoConstant
import com.bintangfajarianto.gmayl.data.datasource.crypto.CryptoNetworkDataSourceModule
import com.bintangfajarianto.gmayl.data.datasource.crypto.CryptoStorageDataSourceModule
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepository
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DataCryptoModule: DI.Module
    get() = DI.Module(name = DataCryptoConstant.MODULE) {
        importOnce(CryptoStorageDataSourceModule)
        importOnce(CryptoNetworkDataSourceModule)

        bindSingleton<CryptoRepository> {
            CryptoRepositoryImpl(
                networkRepository = instance(),
                storageRepository = instance(),
            )
        }
    }
