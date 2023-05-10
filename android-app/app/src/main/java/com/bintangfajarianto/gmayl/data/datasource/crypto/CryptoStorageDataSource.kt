package com.bintangfajarianto.gmayl.data.datasource.crypto

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoStorageRepository
import com.bintangfajarianto.gmayl.data.storage.KeyValueStorage
import com.bintangfajarianto.gmayl.data.storage.StorageKey

class CryptoStorageDataSource(
    private val secureStorage: KeyValueStorage,
) : CryptoStorageRepository {
    override suspend fun saveKeyPair(key: Pair<String, String>) {
        secureStorage.putString(
            key = StorageKey.CRYPTO_PRIVATE_KEY.key,
            value = key.first,
        )
        secureStorage.putString(
            key = StorageKey.CRYPTO_PUBLIC_KEY.key,
            value = key.second,
        )
    }

    override suspend fun getKeyPair(): DataResult<Pair<String, String>> {
        val oldPrivateKey = secureStorage.getString(key = StorageKey.CRYPTO_PRIVATE_KEY.key).orEmpty()
        val oldPublicKey = secureStorage.getString(key = StorageKey.CRYPTO_PUBLIC_KEY.key).orEmpty()
        return Success(oldPrivateKey to oldPublicKey)
    }
}
