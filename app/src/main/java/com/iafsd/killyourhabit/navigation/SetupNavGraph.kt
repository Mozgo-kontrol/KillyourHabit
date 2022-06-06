package com.iafsd.killyourhabit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
@ExperimentalComposeUiApi
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.BottomGraph.route,
        route = NavRoutes.RootGraph.route
    ) {
        loginNavGraph(navController = navController)
        bottomBarNavGraph(navController = navController)
    }
}

/**
 *
 *
 *
 *                       bottom_bar
 *                     /
 *              root <
 *                     \                 /
 *                      login_register <
 *                                      \
 *
 *
 *
 *
 *
 * */