package com.iafsd.killyourhabit.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun KYHCircularProgressIndicator(
    color: Color,
    strokeWidth: Int = 40
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center) {

        CircularProgressIndicator(color = color, strokeWidth = strokeWidth.dp)
    }
}
@Preview(showBackground = true)
@Composable
fun ProgressIndicator(){
    Card(
        modifier = Modifier
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp),
        elevation = 10.dp, shape = CircleShape
    ) {
        KYHCircularProgressIndicator(
            color = MaterialTheme.colors.secondary,
            strokeWidth = 40
        )
    }
}
/*
@Composable
fun KYHCircularProgressIndicatorSample() {
    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(30.dp))
        CircularProgressIndicator()
        Spacer(Modifier.height(30.dp))
        Text("CircularProgressIndicator with progress set by buttons")
        CircularProgressIndicator(progress = animatedProgress)
        Spacer(Modifier.height(30.dp))
        OutlinedButton(
            onClick = {
                if (progress < 1f) progress += 0.1f
            }
        ) {
            Text("Increase")
        }

        OutlinedButton(
            onClick = {
                if (progress > 0f) progress -= 0.1f
            }
        ) {
            Text("Decrease")
        }
    }
}*/
