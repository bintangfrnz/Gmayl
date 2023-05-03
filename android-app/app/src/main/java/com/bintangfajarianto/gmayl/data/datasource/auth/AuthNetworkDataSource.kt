package com.bintangfajarianto.gmayl.data.datasource.auth

import com.bintangfajarianto.gmayl.base.DataResult
import com.bintangfajarianto.gmayl.data.model.auth.response.LoginResponse
import com.bintangfajarianto.gmayl.data.network.networkCall
import com.bintangfajarianto.gmayl.data.repository.auth.AuthNetworkRepository
import kotlin.coroutines.cancellation.CancellationException

internal class AuthNetworkDataSource : AuthNetworkRepository {
    override suspend fun login(email: String, password: String): DataResult<LoginResponse> =
        networkCall {
            if (email != TEMP_VALID_EMAIL || password != TEMP_VALID_PASSWORD) {
                throw CancellationException()
            }

            LoginResponse(email = email)
        }

    companion object {
        internal const val TEMP_VALID_EMAIL = "admin@gmail.com"
        internal const val TEMP_VALID_PASSWORD = "admin123"
    }
}
