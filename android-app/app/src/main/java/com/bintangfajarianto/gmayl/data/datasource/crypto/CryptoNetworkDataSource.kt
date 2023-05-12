package com.bintangfajarianto.gmayl.data.datasource.crypto

import com.bintangfajarianto.gmayl.data.python.BystarBlockCipher
import com.bintangfajarianto.gmayl.data.python.DigitalSign
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoNetworkRepository

internal class CryptoNetworkDataSource(
    private val bystarBlockCipher: BystarBlockCipher,
    private val digitalSign: DigitalSign,
) : CryptoNetworkRepository {
    override suspend fun decryptMail(hexBody: String, symmetricKey: String): String =
        bystarBlockCipher.decryptMessage(hexBody, symmetricKey)

    override suspend fun generateKeyPair(): Pair<String, String> =
        digitalSign.generateKeyPair()

    override suspend fun verify(publicKey: String, message: String, r: String, s: String): Boolean =
        digitalSign.verify(publicKey, message, r, s)
}
