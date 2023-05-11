package com.bintangfajarianto.gmayl.data.repository.crypto

import com.bintangfajarianto.gmayl.base.DataResult

interface CryptoStorageRepository {
    suspend fun saveKeyPair(key: Pair<String, String>)
    suspend fun getKeyPair(): DataResult<Pair<String, String>>
    suspend fun deleteKeyPair()
}
