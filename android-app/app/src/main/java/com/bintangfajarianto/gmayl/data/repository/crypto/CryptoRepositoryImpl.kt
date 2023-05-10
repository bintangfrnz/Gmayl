package com.bintangfajarianto.gmayl.data.repository.crypto

class CryptoRepositoryImpl(
    private val networkRepository: CryptoNetworkRepository,
    private val storageRepository: CryptoStorageRepository,
) : CryptoRepository,
    CryptoNetworkRepository by networkRepository,
    CryptoStorageRepository by storageRepository
