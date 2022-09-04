package com.iafsd.killyourhabit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 *
 *                                      - notification
 *                                   /  - home
 *                       bottom_bar     - settings
 *                     /
 *              root <
 *                     \                 /login
 *                      login_register <
 *                                      \Register
 *
 * */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController, isUserRegistered: Boolean) {

    NavHost(
        navController = navController,
        startDestination = if(isUserRegistered) NavRoutes.BottomGraph.route else NavRoutes.LoginGraph.route,
        route = NavRoutes.RootGraph.route
    ) {
        loginNavGraph(navController = navController)
        bottomBarNavGraph(navController = navController)
    }
}

