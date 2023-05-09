package com.bintangfajarianto.gmayl.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.data.model.auth.User
import com.bintangfajarianto.gmayl.data.model.home.DrawerItemType
import com.bintangfajarianto.gmayl.data.model.home.Mail
import com.bintangfajarianto.gmayl.ui.home.HomeRoute
import com.bintangfajarianto.gmayl.ui.home.detailmail.MailDetailRoute
import com.bintangfajarianto.gmayl.ui.home.sendmail.SendMailRoute

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(route = HomeRoutes.HOME_ROUTE) {
        HomeRoute(navController = navController)
    }

    composable(route = HomeRoutes.HOME_MAIL_DETAIL_ROUTE) {
        val mail = it.arguments?.getParcelable<Mail>(
            HomeRoutes.HOME_MAIL_DETAIL_MAIL_ITEM_ARG,
        ) ?: return@composable
        val mailType = it.arguments?.getParcelable<DrawerItemType>(
            HomeRoutes.HOME_MAIL_DETAIL_MAIL_TYPE_ARG,
        ) ?: return@composable
        MailDetailRoute(mail = mail, mailType = mailType, navController = navController)
    }

    composable(route = HomeRoutes.HOME_SEND_MAIL_ROUTE) {
        val sender = it.arguments?.getParcelable(HomeRoutes.HOME_SEND_MAIL_SENDER_ARG) ?: User()
        val receiver = it.arguments?.getParcelable(HomeRoutes.HOME_SEND_MAIL_RECEIVER_ARG) ?: User()
        SendMailRoute(sender = sender, receiver = receiver, navController = navController)
    }
}

object HomeRoutes {
    const val HOME_ROUTE = "homeRoute"
    const val HOME_ARG = "homeArg"
    const val HOME_MAIL_DETAIL_ROUTE = "homeMailDetailRoute"
    const val HOME_MAIL_DETAIL_MAIL_ITEM_ARG = "homeMailDetailMailItemArg"
    const val HOME_MAIL_DETAIL_MAIL_TYPE_ARG = "homeMailDetailMailTypeArg"
    const val HOME_SEND_MAIL_ROUTE = "homeSendMailRoute"
    const val HOME_SEND_MAIL_SENDER_ARG = "homeSendMailSenderArg"
    const val HOME_SEND_MAIL_RECEIVER_ARG = "homeSendMailReceiverArg"
}
