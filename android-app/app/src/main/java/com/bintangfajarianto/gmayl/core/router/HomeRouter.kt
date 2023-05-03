package com.bintangfajarianto.gmayl.core.router

import com.bintangfajarianto.gmayl.core.RouteDestination

sealed class HomeRouter : RouteDestination() {
    object HomePage : HomeRouter()
}
