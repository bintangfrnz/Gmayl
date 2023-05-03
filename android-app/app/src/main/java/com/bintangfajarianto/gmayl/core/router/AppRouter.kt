package com.bintangfajarianto.gmayl.core.router

import com.bintangfajarianto.gmayl.core.RouteDestination

sealed class AppRouter : RouteDestination() {
    object Logout : AppRouter()
}
