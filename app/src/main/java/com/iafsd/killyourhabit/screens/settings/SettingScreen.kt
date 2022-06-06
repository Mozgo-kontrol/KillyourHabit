package com.iafsd.killyourhabit.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    //init
    viewModel.showADMob(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        viewModel.showADMob(LocalContext.current)
        Text("Profile Screen", style = MaterialTheme.typography.h5)

    }
}


