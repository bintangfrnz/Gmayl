package com.bintangfajarianto.gmayl.core.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.ui.home.HomeRoute
import com.bintangfajarianto.gmayl.ui.home.sendmail.SendMailRoute

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(route = HomeRoutes.HOME_ROUTE) {
        HomeRoute(navController = navController)
    }

    composable(route = HomeRoutes.HOME_DETAIL_MAIL_ROUTE) {
        Text(text = "detail mail")
    }

    composable(route = HomeRoutes.HOME_SEND_MAIL_ROUTE) {
        val user = it.arguments?.getParcelable(HomeRoutes.HOME_SEND_MAIL_ARG) ?: User()
        SendMailRoute(user = user, navController = navController)
    }
}

object HomeRoutes {
    const val HOME_ROUTE = "homeRoute"
    const val HOME_ARG = "homeArg"
    const val HOME_DETAIL_MAIL_ROUTE = "homeDetailMailRoute"
    const val HOME_DETAIL_MAIL_ARG = "homeDetailMailArg"
    const val HOME_SEND_MAIL_ROUTE = "homeSendMailRoute"
    const val HOME_SEND_MAIL_ARG = "homeSendMailArg"
}
