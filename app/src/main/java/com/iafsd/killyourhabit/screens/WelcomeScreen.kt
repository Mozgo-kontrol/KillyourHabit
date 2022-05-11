package com.iafsd.killyourhabit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iafsd.killyourhabit.NavRoutes
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.ui.common.KYHButton

@Composable
fun WelcomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to KYH", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.size(32.dp))
            KYHButton(
                onClickButton = { navController.navigate(NavRoutes.LoginScreen.route) },
                text = stringResource(R.string.login),
                isButtonEnabled = true
            )
            Spacer(modifier = Modifier.size(32.dp))
            KYHButton(
                onClickButton = { navController.navigate(NavRoutes.RegisterScreen.route) },
                text = stringResource(R.string.registration),
                isButtonEnabled = true
            )
        }
    }
}
