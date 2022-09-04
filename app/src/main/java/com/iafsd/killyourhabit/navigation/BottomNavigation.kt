package com.iafsd.killyourhabit.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iafsd.killyourhabit.screens.home.BottomNavItem
import com.iafsd.killyourhabit.screens.home.BottomNavigationBar
import com.iafsd.killyourhabit.screens.home.HomeScreen
import com.iafsd.killyourhabit.screens.notifications.NotificationScreen
import com.iafsd.killyourhabit.screens.settings.SettingScreen
import kotlin.math.roundToInt

@Composable
fun BottomNavigation(navController: NavHostController) {

    val bottomNavController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    // val context: Context = LocalContext.current

    //hideBottom if scroll
    val bottomBarHeight = 55.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value =
                    newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        scaffoldState = scaffoldState,
        bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier
                        .height(bottomBarHeight)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = -bottomBarOffsetHeightPx.value.roundToInt())
                        },
                    items = listOf(
                        BottomNavItem(
                            name = "Home",
                            route = "home",
                            icon = Icons.Default.Home),
                        BottomNavItem(
                            name = "Report",
                            route = "notification",
                            icon = Icons.Default.Notifications,
                            badgeCount = +20),
                        BottomNavItem(
                            name = "Settings",
                            route = "setting",
                            icon = Icons.Default.Settings)
                    ),
                    navController = bottomNavController,
                    onItemClick = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(it.route)
                    })

        }) {

            NavHost(
                navController = bottomNavController,
                startDestination = NavRoutes.HomeScreen.route,
            ) {

                composable(NavRoutes.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
                composable(NavRoutes.SettingScreen.route) {
                    SettingScreen(navController = navController)
                }

                composable(NavRoutes.NotificationScreen.route) {
                    NotificationScreen(navController = navController)
                }
            }
    }
}

