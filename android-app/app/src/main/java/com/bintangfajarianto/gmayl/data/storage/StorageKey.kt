package com.bintangfajarianto.gmayl.data.storage

enum class StorageKey(val key: String, val clearWhenLogout: Boolean) {
    AUTH_IS_LOGIN_KEY(key = "authIsLoginKey", clearWhenLogout = true),
    AUTH_LOGGED_USER_KEY(key = "authLoggedUserKey", clearWhenLogout = true),
    CRYPTO_PRIVATE_KEY(key = "cryptoPrivateKey", clearWhenLogout = false),
    CRYPTO_PUBLIC_KEY(key = "cryptoPublicKey", clearWhenLogout = false),
}
