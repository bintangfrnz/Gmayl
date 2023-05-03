package com.bintangfajarianto.gmayl.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.ui.auth.LoginRoute

fun NavGraphBuilder.authGraph() {
    composable(route = AuthRoutes.LOGIN_ROUTE) {
        LoginRoute()
    }
}

object AuthRoutes {
    const val LOGIN_ROUTE = "loginRoute"
}
