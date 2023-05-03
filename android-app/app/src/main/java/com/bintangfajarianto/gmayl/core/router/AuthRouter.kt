package com.bintangfajarianto.gmayl.core.router

import com.bintangfajarianto.gmayl.core.RouteDestination

sealed class AuthRouter : RouteDestination() {
    object LoginPage : AuthRouter()
}
