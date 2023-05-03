package com.bintangfajarianto.gmayl.data.storage

import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder

class SecureStorage(hawkBuilder: HawkBuilder) : KeyValueStorage {
    init {
        hawkBuilder.build()
    }
    override suspend fun putBoolean(key: String, value: Boolean, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun putDouble(key: String, value: Double, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun putFloat(key: String, value: Float, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun putLong(key: String, value: Long, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun putInt(key: String, value: Int, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun putString(key: String, value: String, clearWhenLogout: Boolean) {
        Hawk.put(key, value)
    }

    override suspend fun remove(key: String) {
        Hawk.delete(key)
    }

    override suspend fun removeAll(logout: Boolean) {
        Hawk.deleteAll()
    }

    override suspend fun getBoolean(key: String): Boolean? = Hawk.get(key)

    override suspend fun getDouble(key: String): Double? = Hawk.get(key)

    override suspend fun getFloat(key: String): Float? = Hawk.get(key)

    override suspend fun getLong(key: String): Long? = Hawk.get(key)

    override suspend fun getInt(key: String): Int? = Hawk.get(key)

    override suspend fun getString(key: String): String? = Hawk.get(key)

    override suspend fun contains(key: String): Boolean = Hawk.get(key)

    override suspend fun getAllKeys(): Set<String> {
        return setOf()
    }

    override suspend fun getCount(): Long = Hawk.count()
}
