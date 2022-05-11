package com.iafsd.killyourhabit


import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iafsd.killyourhabit.screens.NotificationScreen
import com.iafsd.killyourhabit.screens.ProfileScreen
import com.iafsd.killyourhabit.screens.WelcomeScreen
import com.iafsd.killyourhabit.screens.home.HomeScreen
import com.iafsd.killyourhabit.screens.login.LoginScreen
import com.iafsd.killyourhabit.screens.registrieren.RegisterScreen
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.WelcomeScreen.route,
    ) {
        composable(NavRoutes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(NavRoutes.WelcomeScreen.route) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            WelcomeScreen(navController = navController)
        }

        composable(NavRoutes.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(NavRoutes.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(NavRoutes.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(NavRoutes.NotificationScreen.route) {
            NotificationScreen(navController = navController)
        }
    }

}




