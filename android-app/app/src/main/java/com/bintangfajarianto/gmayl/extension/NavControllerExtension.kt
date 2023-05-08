package com.bintangfajarianto.gmayl.extension

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.navigate(
    route: String,
    args: Bundle?,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}

@Composable
fun <T> NavController.GetResult(key: String, onResult: (T) -> Unit) {
    val backstack = remember { currentBackStackEntry }
    val valueScreenResult = backstack
        ?.savedStateHandle
        ?.getLiveData<T>(key)
        ?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)
        currentBackStackEntry?.savedStateHandle?.set(key, null)
        currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    }
}
