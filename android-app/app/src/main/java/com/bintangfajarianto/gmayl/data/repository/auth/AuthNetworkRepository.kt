package com.bintangfajarianto.gmayl.data.repository.auth

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.data.model.auth.response.LoginResponse

interface AuthNetworkRepository {
    suspend fun login(email: String, password: String): DataResult<LoginResponse>
}
