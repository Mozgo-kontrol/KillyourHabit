package com.iafsd.killyourhabit

sealed class NavRoutes (val route: String) {

    object LoginScreen : NavRoutes("login")
    object RegisterScreen : NavRoutes("register")
    object HomeScreen : NavRoutes("home")
    object WelcomeScreen : NavRoutes("welcome")
    object ProfileScreen : NavRoutes("profile")
    object NotificationScreen : NavRoutes("notification")
}