package com.bintangfajarianto.gmayl.data.repository.crypto

import java.math.BigInteger

interface CryptoNetworkRepository {
    suspend fun decryptMail(hexBody: String, symmetricKey: String): String
    suspend fun generateKeyPair(): Pair<String, String>
    suspend fun sign(privateKey: String, message: String): Pair<BigInteger, BigInteger>
    suspend fun verify(publicKey: String, message: String, r: BigInteger, s: BigInteger): Boolean
}
