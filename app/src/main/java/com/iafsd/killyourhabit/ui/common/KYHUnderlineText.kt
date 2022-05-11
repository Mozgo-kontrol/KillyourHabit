package com.iafsd.killyourhabit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun KYHUnderlineText(
    modifier: Modifier = Modifier.fillMaxWidth(),
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    textFirstContent: String,
    textFirstColor : Color? = MaterialTheme.colors.onBackground,
    textSecondContent: String,
    textSecondColor : Color? = Color.Red,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    onClickText: () -> Unit
){
    val textColor1 = textFirstColor ?: MaterialTheme.colors.secondary
    val textColor2 = textSecondColor ?: Color.Red

            Row(modifier,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment
            ) {
                Text(text = textFirstContent, color = textColor1, style = textStyle)
                Spacer(Modifier.width(4.dp))
                Text(modifier = Modifier.clickable(onClick = onClickText),
                    text = textSecondContent, style = textStyle, color = textColor2)
            }
}