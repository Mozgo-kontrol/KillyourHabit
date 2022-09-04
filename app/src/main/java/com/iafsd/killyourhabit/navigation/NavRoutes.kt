package com.iafsd.killyourhabit.navigation


sealed class NavRoutes (val route: String) {
    object BottomNavigation : NavRoutes("bottom_bar_nav")

    //Graphs
    object RootGraph: NavRoutes("root")
    object LoginGraph: NavRoutes("login_register")
    object BottomGraph: NavRoutes("bottom_bar")

    //Screens
    object LoginScreen : NavRoutes("login")
    object RegisterScreen : NavRoutes("register")
    object HomeScreen : NavRoutes("home")
    object WelcomeScreen : NavRoutes("welcome")
    object ProfileScreen : NavRoutes("profile")

    object SettingScreen : NavRoutes("setting")
    object NotificationScreen : NavRoutes("notification")
    object OverviewScreen : NavRoutes("overview")
}