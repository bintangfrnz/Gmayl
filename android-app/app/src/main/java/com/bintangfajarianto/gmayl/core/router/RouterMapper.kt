package com.bintangfajarianto.gmayl.core.router

import android.os.Bundle
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.navigation.AuthRoute

private typealias DestinationType = Pair<String?, Bundle?>

internal fun RouteDestination?.mapToDestination(): DestinationType =
    when (this) {
        is AppRouter -> mapAppRouter()
        is AuthRouter -> mapAuthRouter()
        is HomeRouter -> mapHomeRouter()
        else -> null to null
    }

private fun AppRouter.mapAppRouter(): DestinationType =
    when (this) {
        is AppRouter.Logout -> null to null
    }

private fun AuthRouter.mapAuthRouter(): DestinationType =
    when (this) {
        is AuthRouter.LoginPage -> AuthRoute.LOGIN_ROUTE to null
    }

private fun HomeRouter.mapHomeRouter(): DestinationType =
    when (this) {
        is HomeRouter.HomePage -> "home" to null
    }
