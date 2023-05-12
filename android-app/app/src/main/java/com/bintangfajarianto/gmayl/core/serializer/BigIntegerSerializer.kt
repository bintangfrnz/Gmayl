package com.bintangfajarianto.gmayl.core.serializer

import java.math.BigInteger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive

typealias SerializableBigInteger = @Serializable(with = BigIntegerSerializer::class) BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {

    override val descriptor = PrimitiveSerialDescriptor("java.math.BigInteger", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): BigInteger =
        when (decoder) {
            is JsonDecoder -> decoder.decodeJsonElement().jsonPrimitive.content.toBigInteger()
            else -> decoder.decodeString().toBigInteger()
        }

    override fun serialize(encoder: Encoder, value: BigInteger) =
        when (encoder) {
            is JsonEncoder -> encoder.encodeJsonElement(JsonUnquotedLiteral(value.toString()))
            else -> encoder.encodeString(value.toString())
        }
}
