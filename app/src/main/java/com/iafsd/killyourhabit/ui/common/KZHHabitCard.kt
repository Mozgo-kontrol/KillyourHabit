package com.iafsd.killyourhabit.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iafsd.killyourhabit.data.Habit
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.ui.theme.LilaLight

@Composable
fun KYHHabitCard(
    modifier: Modifier = Modifier,
    cardColor: Color = MaterialTheme.colors.secondary,
    textColor: Color = MaterialTheme.colors.onBackground,
    title: String,
    habit: Habit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenWidthWSP = ((screenWidth - GridUnit.two.dp).value * 0.62).toInt()

    println(screenWidthWSP)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = GridUnit.one.dp,
        shape = RoundedCornerShape(GridUnit.two.dp),
        backgroundColor = cardColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(GridUnit.two.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer(Modifier.height(GridUnit.eight.dp))
            Text(title, style = MaterialTheme.typography.h3, color = textColor)
            Spacer(Modifier.height(GridUnit.two.dp))
            Row(Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .width(screenWidthWSP.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(textAlign = TextAlign.Center,
                        text = "All days :",
                        style = MaterialTheme.typography.h5,
                        color = textColor)
                    Text(textAlign = TextAlign.Center,
                        text = "Days without pause :",
                        style = MaterialTheme.typography.h5,
                        color = Color.Green)
                    Text(textAlign = TextAlign.Center,
                        text = "Missed :",
                        style = MaterialTheme.typography.h5,
                        color = Color.Red)
                    Text(textAlign = TextAlign.Center,
                        text = "Habit Strength :",
                        style = MaterialTheme.typography.h5,
                        color = textColor)
                }
                Column( modifier = Modifier
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(textAlign = TextAlign.Right,
                        text = "17",
                        style = MaterialTheme.typography.h5,
                        color = textColor)
                    Text(textAlign = TextAlign.Right,
                        text = "13",
                        style = MaterialTheme.typography.h5,
                        color = Color.Green)
                    Text(textAlign = TextAlign.Right,
                        text = "4",
                        style = MaterialTheme.typography.h5,
                        color = Color.Red)
                    Text(textAlign = TextAlign.Right,
                        text = " Lower",
                        style = MaterialTheme.typography.h5,
                        color = Color.Yellow)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCard() {
    val title = "Read German 30 min every day"
    val habit = Habit("0", title, daysWithoutPause = 30, daysMissed = 3)
    KYHHabitCard(cardColor = LilaLight, textColor = Color.White, title = title, habit = habit)
}
