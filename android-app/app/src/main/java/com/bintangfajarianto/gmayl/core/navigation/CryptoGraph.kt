package com.bintangfajarianto.gmayl.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.ui.crypto.KeyGeneratorRoute

fun NavGraphBuilder.cryptoGraph(navController: NavController) {
    composable(route = CryptoRoutes.KEY_GENERATOR_ROUTE) {
        KeyGeneratorRoute(navController = navController)
    }
}

object CryptoRoutes {
    const val KEY_GENERATOR_ROUTE = "keyGeneratorRoute"
}
