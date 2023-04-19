package com.bintangfajarianto.gmayl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.bintangfajarianto.gmayl.theme.GmaylTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GmaylTheme {
                GmaylApp(isAuthenticated = false)
            }
        }
    }
}

@Composable
fun GmaylApp(isAuthenticated: Boolean, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(modifier = modifier) {
        GmaylNavHost(
            modifier = Modifier.padding(it),
            isAuthenticated = isAuthenticated,
            navController = navController,
        )
    }
}

@Composable
fun GmaylNavHost(
    isAuthenticated: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isAuthenticated) "home" else "auth",
    ) {
        composable(route = "auth") { Text(text = "auth") }
        composable(route = "home") { Text(text = "home") }
        composable(route = "detail") { Text(text = "detail") }
        composable(route = "send") { Text(text = "send") }
    }
}
