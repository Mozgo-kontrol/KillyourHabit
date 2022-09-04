package com.iafsd.killyourhabit.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.ui.common.KYHCard
import com.iafsd.killyourhabit.ui.theme.LilaLight

@Composable
fun NotificationScreen(
    navController: NavHostController,
    viewModel: NotificationViewModel = hiltViewModel(),
) {

    val messageList = viewModel.messageList.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (messageList.value.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.you_do_not_have_any_notification), style = MaterialTheme.typography.h5)
            }
        } else {

            LazyColumn(modifier = Modifier.fillMaxHeight().padding(all = GridUnit.one.dp)) {
                items(items = messageList.value!!, itemContent = { pushMessage ->
                    KYHCard(cardColor = LilaLight,
                        textColor = Color.White,
                        title = pushMessage.title,
                        message = pushMessage.message,
                        date = pushMessage.messageTime)

                })
            }
        }
    }
}

