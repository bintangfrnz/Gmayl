package com.bintangfajarianto.gmayl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.core.navigation.AuthRoute.LOGIN_PAGE
import com.bintangfajarianto.gmayl.core.navigation.authGraph
import com.bintangfajarianto.gmayl.core.router.AppRouter
import com.bintangfajarianto.gmayl.core.router.AuthRouter
import com.bintangfajarianto.gmayl.core.router.mapToDestination
import com.bintangfajarianto.gmayl.domain.usecase.auth.LoginStatusUseCase
import com.bintangfajarianto.gmayl.extension.navigate
import com.bintangfajarianto.gmayl.theme.GmaylTheme
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import org.kodein.di.instance

class MainActivity : ComponentActivity(), DIAware {

    override val di: DI by closestDI()

    private val loginStatusUseCase: LoginStatusUseCase by instance()
    private val isLogin by lazy { runBlocking { loginStatusUseCase(Unit).isLogin } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Napier.base(DebugAntilog())

        setContent {
            withDI(di = di) {
                GmaylTheme {
                    GmaylApp(isLogin = isLogin)
                }
            }
        }
    }
}

@Composable
fun GmaylApp(isLogin: Boolean, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val routeDestinationHandler: RouteDestinationHandler by rememberInstance()
    val routeDestinationFlow = routeDestinationHandler.channel.receiveAsFlow().collectAsState(
        initial = null,
    )

    DisposableEffect(routeDestinationFlow.value) {
        val routeDestination = routeDestinationFlow.value
        val (route, bundle) = routeDestination.mapToDestination()

        val popUpToRoute = routeDestinationHandler.popUpToRoute.mapToDestination().first.orEmpty()
        val isInclusive = routeDestinationHandler.inclusive

        if (routeDestination is AppRouter.Logout) {
            // If the destination is logout,
            // Pop up to Login and remove session
        }

        if (!route.isNullOrEmpty()) {
            if (popUpToRoute.isNotEmpty()) {
                navController.navigate(
                    route = route,
                    args = bundle,
                    navOptions = navOptions {
                        popUpTo(route = popUpToRoute) {
                            inclusive = isInclusive
                        }
                    },
                )
                routeDestinationHandler.popUpToRoute = null
                routeDestinationHandler.inclusive = true
            } else {
                try {
                    val destination = navController.getBackStackEntry(route).destination
                    val destinationRoute = (destination as? NavGraph)?.startDestinationRoute
                        ?: destination.route

                    if (destinationRoute != null) {
                        Napier.d("Route $destinationRoute is in the backstack, will pop to that stack")
                        navController.popBackStack(destinationRoute, inclusive = false)
                    }
                } catch (ex: IllegalArgumentException) {
                    Napier.d("Route $route is not in the backstack, will navigate normally")
                    navController.navigate(route = route, args = bundle)
                }
            }
        }

        onDispose {}
    }

    Scaffold(modifier = modifier) {
        GmaylNavHost(
            modifier = Modifier.padding(it),
            isLogin = isLogin,
            navController = navController,
        )
    }
}

@Composable
fun GmaylNavHost(
    isLogin: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val routeDestinationHandler: RouteDestinationHandler by rememberInstance()

    val startDestination = when {
        isLogin -> "home"
        else -> LOGIN_PAGE
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph()
        composable(route = "home") {
            Button(
                onClick = {
                    routeDestinationHandler.sendDestination(AuthRouter.LoginPage)
                },
            ) {
                Text(text = "Back to Login")
            }
        }
    }
}
