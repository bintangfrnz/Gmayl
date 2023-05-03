package com.bintangfajarianto.gmayl.data.repository.auth

import com.bintangfajarianto.gmayl.data.model.auth.response.LoginResponse

interface AuthRepository : AuthNetworkRepository, AuthStorageRepository {
    suspend fun transformResponse(response: LoginResponse)
}
