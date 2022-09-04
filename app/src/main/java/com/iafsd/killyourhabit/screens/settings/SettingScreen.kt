package com.iafsd.killyourhabit.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.admob.AdBannerSize
import com.iafsd.killyourhabit.admob.AdMobAdsBanner
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.tools.toast
import com.iafsd.killyourhabit.tools.toastMessage
import com.iafsd.killyourhabit.ui.common.CustomGlobal
import com.iafsd.killyourhabit.ui.common.KYHButton
import com.iafsd.killyourhabit.ui.common.ScreenEvent

@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
) {


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    //init
    // viewModel.showADMob(LocalContext.current)

    val user = viewModel.user.observeAsState()

    val isUserLogin = viewModel.isUserAuth.observeAsState()

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
        viewModel.showADMob(context)
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            if (CustomGlobal.isAdsOn) {
                AdMobAdsBanner(
                    adUnitBannerId = stringResource(R.string.ad_id_banner),
                    adBannerSize = AdBannerSize.ANCHORED_ADAPTIVE_BANNER
                )
            }
        },
        bottomBar = {
        },
        content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        // .verticalScroll(rememberScrollState())
                        .padding(GridUnit.two.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.height(GridUnit.eight.dp))
                    Text("Settings Screen", style = MaterialTheme.typography.h2)
                    Spacer(Modifier.height(GridUnit.two.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = GridUnit.one.dp,
                        shape = RoundedCornerShape(GridUnit.two.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(GridUnit.two.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Welcome ${user.value?.nickname}",
                                style = MaterialTheme.typography.h5)
                            Spacer(Modifier.height(GridUnit.one.dp))
                            Text("User is authenticated : ${isUserLogin.value}",
                                style = MaterialTheme.typography.h5)
                            Spacer(Modifier.height(GridUnit.one.dp))
                            Text("User id : ${user.value?.id}",
                                style = MaterialTheme.typography.h5)
                            Spacer(Modifier.height(GridUnit.one.dp))
                            Text("Email : ${user.value?.email}",
                                style = MaterialTheme.typography.h5)
                            Spacer(Modifier.height(GridUnit.one.dp))
                        }
                    }

                    Spacer(Modifier.height(GridUnit.two.dp))
                    KYHButton(
                        onClickButton = viewModel::logout,
                        text = stringResource(R.string.log_out),
                        isButtonEnabled = true
                    )
                }
        }
    )
}
/* Box(
     modifier = Modifier
         .fillMaxSize(),
     contentAlignment = Alignment.Center
 ) {
     viewModel.showADMob(LocalContext.current)
     Text("Profile Screen", style = MaterialTheme.typography.h5)

     Text("user is authenticated : ${isUserLogin.value}",
         style = MaterialTheme.typography.h5)

     Text("Email ${user.value?.email}",
         style = MaterialTheme.typography.h5)

     Spacer(Modifier.height(32.dp))

     KYHButton(onClickButton = viewModel::logout,
         text = stringResource(R.string.log_out),
         isButtonEnabled = true
     )
 }*/



