package com.iafsd.killyourhabit.navigation

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iafsd.killyourhabit.screens.NotificationScreen
import com.iafsd.killyourhabit.screens.home.BottomNavItem
import com.iafsd.killyourhabit.screens.home.BottomNavigationBar
import com.iafsd.killyourhabit.screens.home.HomeScreen
import com.iafsd.killyourhabit.screens.settings.SettingScreen

@Composable
fun BottomNavigation(navController: NavHostController){

    val bottomNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val context : Context = LocalContext.current

    //load the interstitial ad
    //loadInterstitial(context)
    //add the interstitial ad callbacks
   //addInterstitialCallbacks(context)

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier,
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "home",
                        icon = Icons.Default.Home),
                    BottomNavItem(
                        name = "Report",
                        route = "notification",
                        icon = Icons.Default.Notifications,
                        badgeCount = 23),
                    BottomNavItem(
                        name = "Settings",
                        route = "setting",
                        icon = Icons.Default.Settings)
                ),
                navController = bottomNavController,
                onItemClick = {
                    bottomNavController.popBackStack()
                    bottomNavController.navigate(it.route)})

        } ) {
        NavHost(
            navController = bottomNavController,
            startDestination = NavRoutes.HomeScreen.route,
        ) {

            composable(NavRoutes.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
             //TODO
            composable(NavRoutes.SettingScreen.route) {
                SettingScreen(navController = navController)
            }

            composable(NavRoutes.NotificationScreen.route) {
                NotificationScreen(navController = navController)
            }
        }
    }
}

