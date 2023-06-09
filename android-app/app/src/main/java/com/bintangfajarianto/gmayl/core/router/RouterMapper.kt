package com.bintangfajarianto.gmayl.core.router

import android.os.Bundle
import androidx.core.os.bundleOf
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.navigation.AuthRoutes
import com.bintangfajarianto.gmayl.core.navigation.CryptoRoutes
import com.bintangfajarianto.gmayl.core.navigation.HomeRoutes

private typealias DestinationType = Pair<String?, Bundle?>

internal fun RouteDestination?.mapToDestination(): DestinationType =
    when (this) {
        is AppRouter -> mapAppRouter()
        is AuthRouter -> mapAuthRouter()
        is CryptoRouter -> mapCryptoRouter()
        is HomeRouter -> mapHomeRouter()
        else -> null to null
    }

private fun AppRouter.mapAppRouter(): DestinationType =
    when (this) {
        is AppRouter.Logout -> null to null
    }

private fun AuthRouter.mapAuthRouter(): DestinationType =
    when (this) {
        is AuthRouter.LoginPage -> AuthRoutes.LOGIN_ROUTE to null
    }

private fun CryptoRouter.mapCryptoRouter(): DestinationType =
    when (this) {
        is CryptoRouter.KeyGeneratorPage -> CryptoRoutes.KEY_GENERATOR_ROUTE to null
    }

private fun HomeRouter.mapHomeRouter(): DestinationType =
    when (this) {
        is HomeRouter.HomePage -> HomeRoutes.HOME_ROUTE to null
        is HomeRouter.DetailMailPage -> HomeRoutes.HOME_MAIL_DETAIL_ROUTE to bundleOf(
            HomeRoutes.HOME_MAIL_DETAIL_MAIL_ITEM_ARG to mail,
            HomeRoutes.HOME_MAIL_DETAIL_MAIL_TYPE_ARG to mailType,
        )
        is HomeRouter.SendMailPage -> HomeRoutes.HOME_SEND_MAIL_ROUTE to bundleOf(
            HomeRoutes.HOME_SEND_MAIL_SENDER_ARG to sender,
            HomeRoutes.HOME_SEND_MAIL_RECEIVER_ARG to receiver,
        )
    }
