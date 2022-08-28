package com.example.punkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.punkapp.ui.theme.PunkAppTheme
import com.example.punkapp.view.DetailView
import com.example.punkapp.view.OverviewView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    companion object {
        val LocalNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        PunkAppTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                NavigationHost()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavigationHost() {

        val navController: NavHostController = rememberAnimatedNavController()

        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            AnimatedNavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = "overviewView"
            ) {
                composable("overviewView") {
                    OverviewView()
                }
                composable(
                    "detailView/{beerId}",
                    arguments = listOf(navArgument("beerId") { type = NavType.IntType }),
                    enterTransition = {
                        // Let's make for a really long fade in
                        expandVertically(animationSpec = tween(200))
                    }
                ) {
                    it.arguments?.getInt("beerId")?.also { id ->
                        DetailView(id)
                    }
                }
            }
        }
    }


}