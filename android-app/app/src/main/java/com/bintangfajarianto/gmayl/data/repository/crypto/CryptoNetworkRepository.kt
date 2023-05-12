package com.bintangfajarianto.gmayl.data.repository.crypto

interface CryptoNetworkRepository {
    suspend fun decryptMail(hexBody: String, symmetricKey: String): String
    suspend fun generateKeyPair(): Pair<String, String>
    suspend fun verify(publicKey: String, message: String, r: String, s: String): Boolean
}
