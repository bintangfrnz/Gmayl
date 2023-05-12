package com.bintangfajarianto.gmayl.data.datasource.crypto

import com.bintangfajarianto.gmayl.data.python.BystarBlockCipher
import com.bintangfajarianto.gmayl.data.python.DigitalSign
import com.bintangfajarianto.gmayl.data.repository.crypto.CryptoNetworkRepository
import java.math.BigInteger

internal class CryptoNetworkDataSource(
    private val bystarBlockCipher: BystarBlockCipher,
    private val digitalSign: DigitalSign,
) : CryptoNetworkRepository {
    override suspend fun decryptMail(hexBody: String, symmetricKey: String): String =
        bystarBlockCipher.decryptMessage(hexBody, symmetricKey)

    override suspend fun generateKeyPair(): Pair<String, String> =
        digitalSign.generateKeyPair()

    override suspend fun sign(privateKey: String, message: String): Pair<BigInteger, BigInteger> =
        digitalSign.sign(privateKey, message)

    override suspend fun verify(publicKey: String, message: String, r: BigInteger, s: BigInteger): Boolean =
        digitalSign.verify(publicKey, message, r, s)
}
