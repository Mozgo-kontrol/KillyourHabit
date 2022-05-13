package com.iafsd.killyourhabit.ui.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun KYHButton(
    text: String,
    isButtonEnabled: Boolean,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
    shapeButton: Shape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    elevationButton: ButtonElevation = ButtonDefaults.elevation(
        defaultElevation = 4.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
) {
    Button(
        modifier = modifier.defaultMinSize(minWidth = 128.dp),
        onClick = onClickButton,
        enabled = isButtonEnabled,
        elevation = elevationButton,
        shape = shapeButton,
    ) {
        Text(text = text, style = textStyle)
    }
}
//@Preview(showBackground = true)
@Composable
fun PreviewButton(){
        KYHButton("Login",true, { })
}

