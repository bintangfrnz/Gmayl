package com.bintangfajarianto.gmayl.data.storage

interface KeyValueStorage : ReadOnlyKeyValueStorage {
    suspend fun putBoolean(key: String, value: Boolean, clearWhenLogout: Boolean = true)

    suspend fun putDouble(key: String, value: Double, clearWhenLogout: Boolean = true)

    suspend fun putFloat(key: String, value: Float, clearWhenLogout: Boolean = true)

    suspend fun putLong(key: String, value: Long, clearWhenLogout: Boolean = true)

    suspend fun putInt(key: String, value: Int, clearWhenLogout: Boolean = true)

    suspend fun putString(key: String, value: String, clearWhenLogout: Boolean = true)

    suspend fun remove(key: String)

    suspend fun removeAll(logout: Boolean = true)
}
