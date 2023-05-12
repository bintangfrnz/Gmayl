package com.bintangfajarianto.gmayl.core.serializer

import io.github.aakira.napier.Napier
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

private val JsonSerializer = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

fun <T> String.decodeTo(deserializer: DeserializationStrategy<T>): T? =
    try {
        JsonSerializer.decodeFromString(deserializer, this)
    } catch (e: Exception) {
        Napier.e(
            " ${e.printStackTrace()}",
            tag = "JsonSerializer - Failed to decodeFromString",
        )
        null
    }

fun <T> T.encodeToString(serializer: SerializationStrategy<T>): String =
    try {
        JsonSerializer.encodeToString(serializer, this)
    } catch (e: Exception) {
        Napier.e(
            " ${e.printStackTrace()}",
            tag = "JsonSerializer - Failed to encodeToString",
        )
        ""
    }
