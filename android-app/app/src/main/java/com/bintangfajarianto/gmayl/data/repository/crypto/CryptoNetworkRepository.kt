package com.bintangfajarianto.gmayl.data.repository.crypto

interface CryptoNetworkRepository {
    suspend fun decryptMail(hexBody: String, symmetricKey: String): String
    suspend fun generateKeyPair(): Pair<String, String>
    suspend fun sign(privateKey: String, message: String): Pair<Int, Int>
    suspend fun verify(publicKey: String, message: String, r: Int, s: Int): Boolean
}
