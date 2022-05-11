package com.iafsd.killyourhabit.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.iafsd.killyourhabit.NavRoutes
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.toast
import com.iafsd.killyourhabit.toastMessage
import com.iafsd.killyourhabit.ui.common.KYHButton
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import kotlinx.coroutines.flow.collect


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val TAG = "HomeScreen"
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.ShowToastString -> context.toastMessage(event.message)
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.MoveToScreen -> {
                    navController.popBackStack()
                    navController.navigate(event.navRoutes)
                }
                else -> {}
            }
        }
    }

    Scaffold(
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
                    route = "profile",
                    icon = Icons.Default.Settings)
            ),
            navController = navController,
            onItemClick = {navController.navigate(it.route)})
    } ){

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Home Screen", style = MaterialTheme.typography.h5)

            Text("Welcome ${viewModel.user.value?.nickname}", style = MaterialTheme.typography.h5)

            Spacer(Modifier.height(32.dp))

            KYHButton(onClickButton = { navController.navigate(NavRoutes.ProfileScreen.route)},
                text = stringResource(R.string.profile_screen),
                isButtonEnabled = true
            )
        }

    }
}

