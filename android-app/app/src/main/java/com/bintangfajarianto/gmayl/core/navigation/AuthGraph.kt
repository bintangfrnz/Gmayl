package com.bintangfajarianto.gmayl.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.ui.auth.LoginRoute

fun NavGraphBuilder.authGraph() {
    composable(AuthRoute.LOGIN_ROUTE) {
        LoginRoute()
    }
}

object AuthRoute {
    const val LOGIN_ROUTE = "loginRoute"
}
