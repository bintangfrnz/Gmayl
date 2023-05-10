package com.bintangfajarianto.gmayl.data.storage

interface ReadOnlyKeyValueStorage {
    suspend fun getBoolean(key: String): Boolean?

    suspend fun getDouble(key: String): Double?

    suspend fun getFloat(key: String): Float?

    suspend fun getLong(key: String): Long?

    suspend fun getInt(key: String): Int?

    suspend fun getString(key: String): String?

    suspend fun contains(key: String): Boolean

    suspend fun getCount(): Long
}
