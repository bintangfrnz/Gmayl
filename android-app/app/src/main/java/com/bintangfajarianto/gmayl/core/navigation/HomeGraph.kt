package com.bintangfajarianto.gmayl.core.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.ui.home.HomeRoute

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(route = HomeRoutes.HOME_ROUTE) {
        HomeRoute()
    }

    composable(route = HomeRoutes.HOME_DETAIL_MAIL_ROUTE) {
        Text(text = "detail mail")
    }

    composable(route = HomeRoutes.HOME_SEND_MAIL_ROUTE) {
        Text(text = "send mail")
    }
}

object HomeRoutes {
    const val HOME_ROUTE = "homeRoute"
    const val HOME_DETAIL_MAIL_ROUTE = "homeDetailMailRoute"
    const val HOME_DETAIL_MAIL_ARG = "homeDetailMailArg"
    const val HOME_SEND_MAIL_ROUTE = "homeSendMailRoute"
}
