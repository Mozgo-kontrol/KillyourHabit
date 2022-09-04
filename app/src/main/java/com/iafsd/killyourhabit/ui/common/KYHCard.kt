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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iafsd.killyourhabit.tools.GridUnit
import com.iafsd.killyourhabit.ui.theme.LilaLight

@Composable
fun KYHCard(
    modifier: Modifier = Modifier.fillMaxWidth(),
    cardColor: Color = MaterialTheme.colors.secondary,
    textColor: Color = MaterialTheme.colors.onBackground,
    title: String,
    message: String,
    date: String
) {
    Card(
        modifier = modifier,
        elevation = GridUnit.one.dp,
        shape = RoundedCornerShape(GridUnit.half.dp),
        backgroundColor = cardColor
    ) {
        Column(
            modifier = modifier
                .padding(GridUnit.two.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(textAlign = TextAlign.Center,
                text =  message,
                style = MaterialTheme.typography.body1,
                color = textColor)

            Spacer(Modifier.height(GridUnit.one.dp))
            Row( modifier = modifier,
                horizontalArrangement = Arrangement.End) {
                Text(textAlign = TextAlign.End,
                    text =  date,
                    style = MaterialTheme.typography.body2,
                    color = textColor)
            }
        }


    }
    Spacer(Modifier.height(GridUnit.one.dp))
}

@Preview(showBackground = true)
@Composable
fun KYHCard() {

    KYHCard(cardColor = LilaLight, textColor = Color.White, title = "sdfdsad",
        message = "dsadfsfsdk skdsdk  kdasdk aksk  askdsakdksa ", date = "02.03.2021")
}