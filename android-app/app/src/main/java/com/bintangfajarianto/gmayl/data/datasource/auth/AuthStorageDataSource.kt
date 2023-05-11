package com.bintangfajarianto.gmayl.data.datasource.auth

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.decodeTo
import com.bintangfajarianto.gmayl.data.encodeToString
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.repository.auth.AuthStorageRepository
import com.bintangfajarianto.gmayl.data.storage.KeyValueStorage
import com.bintangfajarianto.gmayl.data.storage.StorageKey

internal class AuthStorageDataSource(
    private val secureStorage: KeyValueStorage,
) : AuthStorageRepository {
    override suspend fun saveUser(user: User) {
        secureStorage.putString(
            key = StorageKey.AUTH_LOGGED_USER_KEY.key,
            value = User(user).encodeToString(User.serializer()),
        )
    }

    override suspend fun getUser(): DataResult<User> {
        val old = secureStorage.getString(StorageKey.AUTH_LOGGED_USER_KEY.key)
            ?.decodeTo(User.serializer()) ?: return Error(Throwable("User is not available"))
        return Success(old)
    }

    override suspend fun setLogin(isLogin: Boolean) {
        secureStorage.putBoolean(StorageKey.AUTH_IS_LOGIN_KEY.key, isLogin)
    }

    override suspend fun isLogin(): Boolean =
        secureStorage.getBoolean(StorageKey.AUTH_IS_LOGIN_KEY.key) ?: false

    override suspend fun logout() {
        enumValues<StorageKey>().forEach { storageKey ->
            if (storageKey.clearWhenLogout) {
                secureStorage.remove(storageKey.key)
            }
        }
        setLogin(false)
    }
}
