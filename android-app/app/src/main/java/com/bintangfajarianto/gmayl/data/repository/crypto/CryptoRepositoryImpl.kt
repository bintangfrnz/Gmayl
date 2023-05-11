package com.bintangfajarianto.gmayl.data.repository.crypto

class CryptoRepositoryImpl(
    networkRepository: CryptoNetworkRepository,
    storageRepository: CryptoStorageRepository,
) : CryptoRepository,
    CryptoNetworkRepository by networkRepository,
    CryptoStorageRepository by storageRepository
