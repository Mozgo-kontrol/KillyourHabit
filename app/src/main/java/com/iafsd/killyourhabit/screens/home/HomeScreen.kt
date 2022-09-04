package com.iafsd.killyourhabit.screens.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.admob.AdBannerSize
import com.iafsd.killyourhabit.admob.AdMobAdsBanner
import com.iafsd.killyourhabit.data.Habit
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.tools.toast
import com.iafsd.killyourhabit.tools.toastMessage
import com.iafsd.killyourhabit.ui.common.CustomGlobal.isAdsOn
import com.iafsd.killyourhabit.ui.common.KYHHabitCard
import com.iafsd.killyourhabit.ui.common.ScreenEvent
import com.iafsd.killyourhabit.ui.theme.LilaLight

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val user = viewModel.user.observeAsState()

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val showPB = remember { mutableStateOf(false) }

    //TODO
    val habit = Habit("0", "Make sport 30 min every day")

    LaunchedEffect(Unit) {

        Log.wtf("HomeScreen", "LaunchedEffect")

        events.collect { event ->
            when (event) {
                //Loading
                is ScreenEvent.Loading -> showPB.value = event.show
                is ScreenEvent.ShowToastString -> context.toastMessage(event.message)
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.MoveToScreen -> {
                    navController.popBackStack()
                    navController.navigate(event.navRoutes)
                }
                else -> {

                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            if (isAdsOn) {
                AdMobAdsBanner(
                    adUnitBannerId = stringResource(R.string.ad_id_banner),
                    adBannerSize = AdBannerSize.ANCHORED_ADAPTIVE_BANNER
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.padding(bottom = GridUnit.eight.dp),
                onClick = viewModel::showMessageFabButton) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.End,

        ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(GridUnit.one.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showPB.value) {
                Card(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 64.dp, minHeight = 64.dp),
                    elevation = 10.dp, shape = CircleShape
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        strokeWidth = 4.dp
                    )
                }
            } else {
                Text("Home Screen, welcome ${user.value?.nickname} ",
                    style = MaterialTheme.typography.h5)

                KYHHabitCard(cardColor = LilaLight,
                    textColor = Color.White,
                    title = habit.title,
                    habit = habit)

            }

        }

    }

}




