package com.bintangfajarianto.gmayl.data.datasource.auth

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.base.Error
import com.bintangfajarianto.gmayl.base.Success
import com.bintangfajarianto.gmayl.data.decodeTo
import com.bintangfajarianto.gmayl.data.encodeToString
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.repository.auth.AuthStorageRepository
import com.bintangfajarianto.gmayl.data.storage.KeyValueStorage

internal class AuthStorageDataSource(
    private val secureStorage: KeyValueStorage,
) : AuthStorageRepository {
    override suspend fun saveUser(user: User) {
        secureStorage.putString(
            key = AUTH_LOGGED_USER_KEY,
            value = User(user).encodeToString(User.serializer()),
            clearWhenLogout = true,
        )
    }

    override suspend fun getUser(): DataResult<User> {
        val old = secureStorage.getString(AUTH_LOGGED_USER_KEY)?.decodeTo(User.serializer())
            ?: return Error(Throwable("User is not available"))
        return Success(old)
    }

    override suspend fun setLogin(isLogin: Boolean) {
        secureStorage.putBoolean(AUTH_IS_LOGIN_KEY, isLogin)
    }

    override suspend fun isLogin(): Boolean =
        secureStorage.getBoolean(AUTH_IS_LOGIN_KEY) ?: false

    override suspend fun logout(): Boolean {
        secureStorage.removeAll(true)
        setLogin(false)
        return true
    }

    companion object {
        internal const val AUTH_IS_LOGIN_KEY = "authIsLoginKey"
        internal const val AUTH_LOGGED_USER_KEY = "authLoggedUserKey"
    }
}
