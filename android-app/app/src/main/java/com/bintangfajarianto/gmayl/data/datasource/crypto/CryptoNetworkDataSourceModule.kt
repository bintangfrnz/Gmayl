package com.bintangfajarianto.gmayl.data.datasource.crypto

import com.bintangfajarianto.gmayl.data.constant.DataCryptoConstant
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoNetworkRepository
import com.bintangfajarianto.gmayl.di.module.BystarBlockCipherProviderModule
import com.bintangfajarianto.gmayl.di.module.DigitalSignProviderModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val CryptoNetworkDataSourceModule: DI.Module
    get() = DI.Module(name = DataCryptoConstant.NETWORK_DATA_SOURCE_MODULE) {
        importOnce(BystarBlockCipherProviderModule)
        importOnce(DigitalSignProviderModule)

        bindSingleton<CryptoNetworkRepository> {
            CryptoNetworkDataSource(
                bystarBlockCipher = instance(),
                digitalSign = instance(),
            )
        }
    }
