package com.iafsd.killyourhabit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.bottomBarNavGraph (navController: NavHostController) {

    navigation(
        startDestination = NavRoutes.BottomNavigation.route,
        route = NavRoutes.BottomGraph.route
    ) {
        composable(
            route = NavRoutes.BottomNavigation.route,
            content = {
                BottomNavigation(
                    navController = navController
                )
            })
    }

}