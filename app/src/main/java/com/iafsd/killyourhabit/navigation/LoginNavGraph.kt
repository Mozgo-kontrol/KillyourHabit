package com.iafsd.killyourhabit.navigation



import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.iafsd.killyourhabit.screens.login.LoginScreen
import com.iafsd.killyourhabit.screens.registrieren.RegisterScreen

@ExperimentalComposeUiApi
fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = NavRoutes.LoginScreen.route,
        route = NavRoutes.LoginGraph.route
    ) {
        composable(
            route = NavRoutes.LoginScreen.route,
            content = {
                LoginScreen(
                    navController = navController,
                )
            })
        composable(
            route = NavRoutes.RegisterScreen.route,
            content = {
                RegisterScreen(
                    navController = navController,
                )
            })
    }
}