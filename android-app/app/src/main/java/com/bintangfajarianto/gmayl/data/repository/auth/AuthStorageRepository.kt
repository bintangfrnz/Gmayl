package com.bintangfajarianto.gmayl.data.repository.auth

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.data.model.auth.User

interface AuthStorageRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): DataResult<User>
    suspend fun setLogin(isLogin: Boolean)
    suspend fun isLogin(): Boolean
    suspend fun logout(): Boolean
}
