package com.bintangfajarianto.gmayl.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bintangfajarianto.gmayl.ui.crypto.KeyGeneratorRoute

fun NavGraphBuilder.cryptoGraph() {
    composable(route = CryptoRoutes.KEY_GENERATOR_ROUTE) {
        KeyGeneratorRoute()
    }
}

object CryptoRoutes {
    const val KEY_GENERATOR_ROUTE = "keyGeneratorRoute"
}
