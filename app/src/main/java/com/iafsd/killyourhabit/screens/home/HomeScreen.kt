package com.iafsd.killyourhabit.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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
import com.iafsd.killyourhabit.toast
import com.iafsd.killyourhabit.toastMessage
import com.iafsd.killyourhabit.ui.common.CustomGlobal.isAdsOn
import com.iafsd.killyourhabit.ui.common.KYHButton
import com.iafsd.killyourhabit.ui.common.ScreenEvent


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current


    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    var isHomeScreenVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.IsHomeScreenVisible -> {isHomeScreenVisible.value = event.istVisible}
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
        //val name by remember { viewModel.user.value.nickname }
       //viewModel.user.observe{ LifecycleOwner {  }, Observer { name = it.nickname }})

   if(isHomeScreenVisible.value) {
       Scaffold(modifier = Modifier
           .fillMaxSize(),
           topBar = {
               if (isAdsOn) {
                   AdMobAdsBanner(
                       adUnitBannerId = stringResource(R.string.ad_id_banner),
                       adBannerSize = AdBannerSize.ANCHORED_ADAPTIVE_BANNER
                   )
               }
           }

       ) {
           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .verticalScroll(rememberScrollState()),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Text("Home Screen", style = MaterialTheme.typography.h5)

               Text("Welcome ${viewModel.user.value?.nickname}",
                   style = MaterialTheme.typography.h5)

               Spacer(Modifier.height(32.dp))

               KYHButton(onClickButton = viewModel::logout,
                   text = stringResource(R.string.log_out),
                   isButtonEnabled = true
               )
           }
       }
   }

}




