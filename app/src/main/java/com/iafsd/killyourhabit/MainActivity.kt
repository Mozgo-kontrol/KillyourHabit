package com.iafsd.killyourhabit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.iafsd.killyourhabit.ui.theme.KillYourHabitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KillYourHabitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@InternalCoroutinesApi
@Composable
fun Navigation(){
    MainScreen()
}

@Preview(showBackground = true)
@InternalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun DefaultPreview() {
    KillYourHabitTheme {
        Navigation()
    }
}