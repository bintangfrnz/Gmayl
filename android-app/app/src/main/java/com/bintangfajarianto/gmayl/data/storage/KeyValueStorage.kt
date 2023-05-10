package com.bintangfajarianto.gmayl.data.storage

interface KeyValueStorage : ReadOnlyKeyValueStorage {
    suspend fun putBoolean(key: String, value: Boolean)

    suspend fun putDouble(key: String, value: Double)

    suspend fun putFloat(key: String, value: Float)

    suspend fun putLong(key: String, value: Long)

    suspend fun putInt(key: String, value: Int)

    suspend fun putString(key: String, value: String)

    suspend fun remove(key: String)

    suspend fun removeAll(logout: Boolean = true)
}
