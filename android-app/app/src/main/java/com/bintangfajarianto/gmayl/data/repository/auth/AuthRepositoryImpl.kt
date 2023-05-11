package com.bintangfajarianto.gmayl.data.repository.auth

import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.auth.response.LoginResponse

class AuthRepositoryImpl(
    networkRepository: AuthNetworkRepository,
    storageRepository: AuthStorageRepository,
) : AuthRepository,
    AuthNetworkRepository by networkRepository,
    AuthStorageRepository by storageRepository {
    override suspend fun transformResponse(response: LoginResponse) {
        setLogin(true)

        val user = User("Admin", response.email, "")

        saveUser(user)
    }
}
