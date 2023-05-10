package com.bintangfajarianto.gmayl.core.router

import com.bintangfajarianto.gmayl.core.RouteDestination

sealed class CryptoRouter : RouteDestination() {
    object KeyGeneratorPage : CryptoRouter()
}
