package com.bintangfajarianto.gmayl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bintangfajarianto.gmayl.theme.GmaylTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GmaylTheme {
                GmaylApp()
            }
        }
    }
}

@Composable
fun GmaylApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(modifier = modifier) {
        GmaylNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
        )
    }
}

@Composable
fun GmaylNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ".",
    ) {
        composable(route = ".") {
            Text(text = "")
        }
    }
}
